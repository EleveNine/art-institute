package com.elevenine.artinstitute.domain.interactor

import com.elevenine.artinstitute.ui.model.ArtworkListItem
import kotlinx.coroutines.flow.StateFlow

/**
 * @author Sherzod Nosirov
 * @since 02.02.2022
 */

interface FetchPagedArtworksInteractor {
    val artworkItemsFlow: StateFlow<List<ArtworkListItem>>

    suspend fun initInteractor()

    suspend fun requestNextPage()
}