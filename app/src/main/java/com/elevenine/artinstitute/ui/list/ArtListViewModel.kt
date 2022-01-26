package com.elevenine.artinstitute.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elevenine.artinstitute.data.common.DataResult
import com.elevenine.artinstitute.domain.use_case.RequestNewArtworkPageUseCase
import com.elevenine.artinstitute.ui.model.Artwork
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Sherzod Nosirov
 * @since 12.12.2021
 */

@HiltViewModel
class ArtListViewModel @Inject constructor(private val requestNewArtworkPageUseCase: RequestNewArtworkPageUseCase) :
    ViewModel() {

    private val _uiState = MutableLiveData<ArtListUiState>()
    val uiState: LiveData<ArtListUiState>
        get() = _uiState

    private val artworkList = mutableListOf<Artwork>()

    fun requestNewPage() {
        viewModelScope.launch {
            val result = requestNewArtworkPageUseCase()

            when (result) {
                is DataResult.OnSuccess -> {
                    artworkList.clear()
                    artworkList.addAll(result.data)

                    _uiState.postValue(
                        ArtListUiState(
                            artworkList,
                            isBottomLoading = false,
                            showErrorToast = false
                        )
                    )
                }
                is DataResult.OnError -> {
                    _uiState.postValue(
                        ArtListUiState(
                            artworkList,
                            isBottomLoading = false,
                            showErrorToast = true
                        )
                    )

                    result.error.internalException.printStackTrace()
                }
            }
        }

    }

    companion object {
        const val ART_LIST_PAGE_SIZE = 12
    }
}

class ArtListUiState(
    val artworks: List<Artwork>,
    val isBottomLoading: Boolean,
    val showErrorToast: Boolean
)