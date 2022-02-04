package com.elevenine.artinstitute.domain.interactor

import com.elevenine.artinstitute.data.common.DataResult
import com.elevenine.artinstitute.domain.DomainState
import com.elevenine.artinstitute.domain.use_case.GetCachedArtworksFlowUseCase
import com.elevenine.artinstitute.domain.use_case.RequestAndCacheNewArtworkPageUseCase
import com.elevenine.artinstitute.ui.model.ArtworkListItem
import com.elevenine.artinstitute.ui.model.LoadingItem
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * @author Sherzod Nosirov
 * @since 02.02.2022
 */

class FetchPagedArtworksInteractorImpl @Inject constructor(
    private val requestAndCacheNewArtworkPageUseCase: RequestAndCacheNewArtworkPageUseCase,
    private val getCachedArtworksFlowUseCase: GetCachedArtworksFlowUseCase,
) : FetchPagedArtworksInteractor {

    override val artworkItemsFlow: StateFlow<DomainState<List<ArtworkListItem>>>
        get() = _artworkItemsFlow.asStateFlow()

    private val _artworkItemsFlow =
        MutableStateFlow<DomainState<List<ArtworkListItem>>>(DomainState.Loading())

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

                _artworkItemsFlow.value = DomainState.Success(artworkItems)
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
                _artworkItemsFlow.value = DomainState.Error(result.error, artworkItems)
            }
        } else currentPageNumber++

        isLoading = false
    }
}