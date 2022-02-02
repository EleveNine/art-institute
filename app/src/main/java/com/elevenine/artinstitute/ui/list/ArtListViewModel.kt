package com.elevenine.artinstitute.ui.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elevenine.artinstitute.domain.DomainState
import com.elevenine.artinstitute.domain.interactor.FetchPagedArtworksInteractor
import com.elevenine.artinstitute.ui.model.ArtworkListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Sherzod Nosirov
 * @since 12.12.2021
 */

@HiltViewModel
class ArtListViewModel @Inject constructor(private val fetchPagedArtworksInteractor: FetchPagedArtworksInteractor) :
    ViewModel() {

    private val _uiState = MutableLiveData<ArtListUiState>()
    val uiState: LiveData<ArtListUiState>
        get() = _uiState

    init {
        viewModelScope.launch {
            fetchPagedArtworksInteractor.initInteractor()
        }

        viewModelScope.launch {
            fetchPagedArtworksInteractor.artworkItemsFlow.collect { domainState ->
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
            }
        }
    }

    fun requestNewPage() {
        viewModelScope.launch {
            fetchPagedArtworksInteractor.requestNextPage()
        }

    }

    companion object {
        const val ART_LIST_PAGE_SIZE = 12
    }
}

data class ArtListUiState(
    val artworks: List<ArtworkListItem>,
    val isInitialLoading: Boolean,
    val showErrorToast: Boolean
)