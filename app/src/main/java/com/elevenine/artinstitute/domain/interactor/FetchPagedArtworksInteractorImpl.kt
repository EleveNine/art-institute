package com.elevenine.artinstitute.domain.interactor

import com.elevenine.artinstitute.data.common.DataResult
import com.elevenine.artinstitute.domain.use_case.GetCachedArtworksFlowUseCase
import com.elevenine.artinstitute.domain.use_case.RequestAndCacheNewArtworkPageUseCase
import com.elevenine.artinstitute.ui.model.ArtworkListItem
import com.elevenine.artinstitute.ui.model.LoadingItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

/**
 * @author Sherzod Nosirov
 * @since 02.02.2022
 */

class FetchPagedArtworksInteractorImpl @Inject constructor(
    private val requestAndCacheNewArtworkPageUseCase: RequestAndCacheNewArtworkPageUseCase,
    private val getCachedArtworksFlowUseCase: GetCachedArtworksFlowUseCase,
) : FetchPagedArtworksInteractor {

    override val artworkItemsFlow: StateFlow<List<ArtworkListItem>>
        get() = _artworkItemsFlow.asStateFlow()

    private val _artworkItemsFlow =
        MutableStateFlow<List<ArtworkListItem>>(emptyList())

    private var currentPageNumber = 1

    private var artworkItems = mutableListOf<ArtworkListItem>()

    private var isLoading = false

    override suspend fun initInteractor() {
        val result = getCachedArtworksFlowUseCase()

        if (result is DataResult.Success) {
            result.data.collect { artworks ->
                artworkItems.clear()
                artworkItems.addAll(artworks)
                artworkItems.add(LoadingItem(-currentPageNumber))

                _artworkItemsFlow.value = artworkItems
            }
        }
    }

    override suspend fun requestNextPage() {
        if (isLoading) return

        isLoading = true
        val result = requestAndCacheNewArtworkPageUseCase(currentPageNumber)

        if (result is DataResult.Error) {
            if (artworkItems.lastOrNull() is LoadingItem) {
                artworkItems.dropLast(1)
                _artworkItemsFlow.value = listOf()
            }
        } else currentPageNumber++

        isLoading = false
    }
}