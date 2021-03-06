package com.elevenine.artinstitute.domain.repository

import com.elevenine.artinstitute.common.DataResult
import com.elevenine.artinstitute.domain.model.DataListPage
import com.elevenine.artinstitute.ui.model.Artwork

/**
 * @author Sherzod Nosirov
 * @since 12.12.2021
 */

interface ArtResourceRepository {

    suspend fun fetchArtworkListPage(
        pageNumber: Int,
        pageSize: Int,
        categoryId: Long
    ): DataResult<DataListPage<Artwork>>

}