package com.elevenine.artinstitute.ui.artworks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.elevenine.artinstitute.domain.interactor.FetchPagedArtworksInteractor
import com.elevenine.artinstitute.ui.model.ArtworkListItem
import com.elevenine.artinstitute.ui.model.UiMessage
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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
        viewModelScope.launch {
            fetchPagedArtworksInteractor.initInteractor()
        }

/*        fetchPagedArtworksInteractor.artworkItemsFlow.onEach { domainState ->
            when (domainState) {
                is DomainState.Error -> {
                    _uiState.postValue(
                        ArtListUiState(
                            domainState.data ?: emptyList(),
                            isInitialLoading = false,
                            showErrorToast = true
                        )
                    )
                }
                is DomainState.Success -> {
                    _uiState.postValue(
                        ArtListUiState(
                            domainState.data,
                            isInitialLoading = false,
                            showErrorToast = false
                        )
                    )
                }
                is DomainState.Loading -> {
                    val prevState = _uiState.value
                    val newValue = prevState?.copy(isInitialLoading = true)
                    newValue?.let { _uiState.postValue(it) }
                }
            }
        }.launchIn(viewModelScope)*/
    }

    fun requestNewPage() {
        viewModelScope.launch {
            fetchPagedArtworksInteractor.requestNextPage()
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