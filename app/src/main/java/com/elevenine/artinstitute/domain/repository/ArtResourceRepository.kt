package com.elevenine.artinstitute.domain.repository

import com.elevenine.artinstitute.data.common.DataResult
import com.elevenine.artinstitute.data.database.entity.ArtworkEntity

/**
 * @author Sherzod Nosirov
 * @since 12.12.2021
 */

interface ArtResourceRepository {

    suspend fun fetchArtworkListPage(pageNumber: Int): DataResult<List<ArtworkEntity>>

    suspend fun cacheArtworks(artworkList: List<ArtworkEntity>): DataResult<Unit>
}