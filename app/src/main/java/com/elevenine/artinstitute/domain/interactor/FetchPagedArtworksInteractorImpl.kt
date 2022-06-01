package com.elevenine.artinstitute.domain.interactor

import com.elevenine.artinstitute.data.common.EmptyDataError
import com.elevenine.artinstitute.domain.DomainResult
import com.elevenine.artinstitute.domain.interactor.FetchPagedArtworksInteractor.Companion.PAGE_SIZE
import com.elevenine.artinstitute.domain.use_case.GetNextArtworkPageUseCase
import com.elevenine.artinstitute.ui.model.Artwork
import com.elevenine.artinstitute.ui.model.ArtworkListItem
import com.elevenine.artinstitute.ui.model.LoadingItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * @author Sherzod Nosirov
 * @since 02.02.2022
 */

class FetchPagedArtworksInteractorImpl @Inject constructor(
    private val getNextArtworkPageUseCase: GetNextArtworkPageUseCase,
) : FetchPagedArtworksInteractor {

    override val artworkItemsFlow: StateFlow<List<ArtworkListItem>>
        get() = _artworkItemsFlow.asStateFlow()

    private val _artworkItemsFlow =
        MutableStateFlow<List<ArtworkListItem>>(emptyList())

    override val artworks: MutableSet<Artwork> = mutableSetOf()


    // the current page of the paging mechanism, 1-based.
    private var currentPage = 1

    /**
     * The flag that indicates that currently a fetching process is happening and no other calls to
     * [requestNextPage] should be processed.
     */
    private var isLoading = false

    /**
     * The flag that indicates that the end of list is reached and no other calls to
     * [requestNextPage] should be processed.
     */
    private var isEndReached = false

    private var categoryId: Long = -1

    override suspend fun initInteractor(categoryId: Long): DomainResult<Unit> {
        currentPage = 1
        isLoading = false
        isEndReached = false

        this.categoryId = categoryId

        // reset the list of transactions and simply emit the list Loader.
        _artworkItemsFlow.update { listOf(LoadingItem(currentPage)) }

        // request the page of data and return its result
        return requestNextPage()
    }

    override suspend fun requestNextPage(): DomainResult<Unit> {
        if (isLoading || isEndReached) return DomainResult.Success(Unit)

        isLoading = true

        delay(200)

        // check if the current page index is valid for requesting next page by simply checking
        // the size of the artworks list stored in this class. This is necessary for the cases
        // when refreshTransactions() method is called and the currentPageIndex var is changed.
        // Moreover, calling this method may bring new items to the transactions list, thus the
        // new actual page index is required for requesting new page of data.
        if (currentPage < artworks.size / PAGE_SIZE) currentPage = artworks.size / PAGE_SIZE

        val result = getNextArtworkPageUseCase.invoke(currentPage, PAGE_SIZE)

        return when (result) {
            is DomainResult.Error -> {
                isLoading = false

                if (result.error is EmptyDataError) isEndReached = false

                _artworkItemsFlow.update { currentList ->
                    // remove the list loader from the current list of transactions if an error
                    // during the fetch process occurred.
                    if (currentList.isNotEmpty() && currentList[currentList.size - 1] is LoadingItem)
                        currentList.dropLast(1)
                    else currentList
                }
                DomainResult.Error(result.error)
            }
            is DomainResult.Success -> {
                isLoading = false

                artworks += result.data.list

                if (currentPage == 1 && result.data.list.isEmpty()) {
                    // if the first page is already empty, stop fetching process
                    isEndReached = true

                    _artworkItemsFlow.update { currentList ->
                        currentList.dropLast(1)
                    }

                } else if (!result.data.hasMore) {
                    // if the current page has flag that indicates there is no next page, stop
                    // fetching
                    isEndReached = true

                    val uiItems = artworks
                    _artworkItemsFlow.update {
                        currentPage++

                        uiItems.toList()
                    }

                } else {
                    val uiItems =
                        artworks + LoadingItem(currentPage)
                    _artworkItemsFlow.update {
                        currentPage++

                        uiItems.toList()
                    }
                }

                DomainResult.Success(Unit)
            }
        }
    }

    override suspend fun refreshArtworks(): DomainResult<Unit> {
        currentPage = 1
        isEndReached = false

        suspend fun fetchTransactions(): DomainResult<Unit> {
            isLoading = true
            val result = getNextArtworkPageUseCase.invoke(currentPage, PAGE_SIZE)

            when (result) {
                is DomainResult.Error -> {
                    isLoading = false
                    return result
                }
                is DomainResult.Success -> {
                    val fetchedList = result.data.list

                    val lastFetchedItem = fetchedList.lastOrNull()

                    if (artworks.contains(lastFetchedItem)) {
                        artworks += fetchedList
                        val uiItems = artworks.toList()
                        _artworkItemsFlow.update {
                            uiItems
                        }
                        isLoading = false
                        return DomainResult.Success(Unit)
                    } else {
                        artworks += fetchedList
                        val uiItems = artworks.toList()
                        _artworkItemsFlow.update {
                            uiItems
                        }
                        currentPage++
                        fetchTransactions()
                    }
                }
            }
            return DomainResult.Success(Unit)
        }

        return fetchTransactions()
    }
}