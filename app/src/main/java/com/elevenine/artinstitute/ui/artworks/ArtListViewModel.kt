package com.elevenine.artinstitute.ui.artworks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.elevenine.artinstitute.common.onError
import com.elevenine.artinstitute.domain.interactor.FetchPagedArtworksInteractor
import com.elevenine.artinstitute.ui.model.ArtworkListItem
import com.elevenine.artinstitute.ui.model.UiMessage
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

/**
 * @author Sherzod Nosirov
 * @since 12.12.2021
 */

class ArtListViewModel(
    private val categoryId: Long,
    private val fetchPagedArtworksInteractor: FetchPagedArtworksInteractor
) : ViewModel() {

    private val _uiState = MutableStateFlow(ArtListUiState())
    val uiState: StateFlow<ArtListUiState>
        get() = _uiState

    init {
        // collect the lists of artwork items emitted by the fetchPagedArtworksInteractor.
        fetchPagedArtworksInteractor.artworkItemsFlow.onEach { items ->
            _uiState.update { currentUiState ->
                currentUiState.copy(artworks = items)
            }
        }.launchIn(viewModelScope)


        viewModelScope.launch {
            val result = fetchPagedArtworksInteractor.initInteractor(categoryId)

            result.onError { error ->
                _uiState.update { currentUiState ->
                    val toastMessages =
                        currentUiState.toastMessages + UiMessage(
                            UUID.randomUUID().mostSignificantBits,
                            messageResId = error.fallbackMessageId,
                            message = error.errorMessage
                        )

                    currentUiState.copy(
                        toastMessages = toastMessages
                    )
                }
            }
        }
    }

    fun requestNewPage() {
        viewModelScope.launch {
            val result = fetchPagedArtworksInteractor.requestNextPage()

            result.onError { error ->
                _uiState.update { currentUiState ->
                    val toastMessages =
                        currentUiState.toastMessages + UiMessage(
                            UUID.randomUUID().mostSignificantBits,
                            messageResId = error.fallbackMessageId,
                            message = error.errorMessage
                        )

                    currentUiState.copy(
                        toastMessages = toastMessages
                    )
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

    companion object {
        const val ART_LIST_PAGE_SIZE = 12
    }
}

data class ArtListUiState(
    val artworks: List<ArtworkListItem> = emptyList(),
    val isLoading: Boolean = false,

    /**
     * The list of messages to be shown in the queue
     */
    val toastMessages: List<UiMessage> = emptyList()
)


class ArtListViewModelFactory @AssistedInject constructor(
    @Assisted("category_id") private val categoryId: Long,
    private val fetchPagedArtworksInteractor: FetchPagedArtworksInteractor
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass == ArtListViewModel::class.java)
        return ArtListViewModel(categoryId, fetchPagedArtworksInteractor) as T
    }

    @AssistedFactory
    interface Creator {

        fun create(@Assisted("category_id") categoryId: Long): ArtListViewModelFactory
    }
}