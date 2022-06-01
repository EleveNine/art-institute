package com.elevenine.artinstitute.ui.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.elevenine.artinstitute.common.onError
import com.elevenine.artinstitute.common.onSuccessSuspend
import com.elevenine.artinstitute.domain.use_case.SyncAndGetCategoriesFlowUseCase
import com.elevenine.artinstitute.ui.model.Category
import com.elevenine.artinstitute.ui.model.UiMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class CategoriesViewModel(private val syncAndGetCategoriesFlowUseCase: SyncAndGetCategoriesFlowUseCase) :
    ViewModel() {

    private val _uiState = MutableStateFlow(CategoriesUiState())
    val uiState: StateFlow<CategoriesUiState>
        get() = _uiState

    init {
        _uiState.update { currentUiState ->
            currentUiState.copy(isLoading = true)
        }

        viewModelScope.launch {
            val syncResult = syncAndGetCategoriesFlowUseCase()

            syncResult.onSuccessSuspend { flow ->
                viewModelScope.launch {
                    flow.collect { list ->
                        _uiState.update { currentUiState ->
                            currentUiState.copy(categories = list, isLoading = false)
                        }
                    }
                }
            }.onError { error ->
                _uiState.update { currentUiState ->
                    val toastMessages =
                        currentUiState.toastMessages + UiMessage(
                            UUID.randomUUID().mostSignificantBits,
                            messageResId = error.fallbackMessageId,
                            message = error.errorMessage
                        )

                    currentUiState.copy(isLoading = false, toastMessages = toastMessages)
                }
            }
        }
    }

    /**
     * Call this function when the toast message is shown on the screen.
     *
     * @param messageId id of the [UiMessage] that is already shown.
     */
    fun onToastMessageShown(messageId: Long) {
        _uiState.update { currentUiState ->
            val messages = currentUiState.toastMessages.filterNot { it.id == messageId }
            currentUiState.copy(toastMessages = messages)
        }
    }
}


data class CategoriesUiState(
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = false,

    /**
     * The list of messages to be shown in the queue
     */
    val toastMessages: List<UiMessage> = emptyList()
)


class CategoriesViewModelFactory @Inject constructor(
    private val syncAndGetCategoriesFlowUseCase: SyncAndGetCategoriesFlowUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass == CategoriesViewModel::class.java)
        return CategoriesViewModel(syncAndGetCategoriesFlowUseCase) as T
    }
}
