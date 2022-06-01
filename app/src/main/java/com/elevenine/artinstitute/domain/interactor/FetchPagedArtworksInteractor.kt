package com.elevenine.artinstitute.domain.interactor

import com.elevenine.artinstitute.domain.DomainResult
import com.elevenine.artinstitute.ui.model.Artwork
import com.elevenine.artinstitute.ui.model.ArtworkListItem
import kotlinx.coroutines.flow.StateFlow

/**
 * @author Sherzod Nosirov
 * @since 02.02.2022
 */

/**
 * Interactor class that facilitates the logic behind history's pagination, storing and filtration.
 * The ViewModels that use this interactor should observe the [artworkItemsFlow] that emits
 * updated list of transactions based on the current filter values and pagination config.
 *
 * In order to request the new page of data, call [requestNextPage].
 *
 */
interface FetchPagedArtworksInteractor {
    companion object {

        const val PAGE_SIZE = 20
    }

    /**
     * The [StateFlow] instance that emits the current list of transactions based on the current
     * filtering and pagination params. Internally, it produces all subtypes of the
     * [ArtworkListItem], so that the UI consumer can use the provided list of items without the
     * necessity to perform mapping modifications.
     */
    val artworkItemsFlow: StateFlow<List<ArtworkListItem>>

    /**
     * Set of [Artwork] instances only. It is used to properly control the state of the
     * pagination and to avoid overlapping and errors in getting and providing transaction data.
     */
    val artworks: MutableSet<Artwork>

    suspend fun initInteractor(categoryId: Long): DomainResult<Unit>

    /**
     * Request the next page of data.
     *
     * @return the result of the fetching operation.
     */
    suspend fun requestNextPage(): DomainResult<Unit>

    /**
     * Refreshes the list of the transactions by requesting fresh data from the remote API again
     * until all the new transactions not present in [artworks] are received.
     */
    suspend fun refreshArtworks(): DomainResult<Unit>
}