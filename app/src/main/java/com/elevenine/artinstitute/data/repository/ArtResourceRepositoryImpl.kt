package com.elevenine.artinstitute.data.repository

import com.elevenine.artinstitute.common.DataResult
import com.elevenine.artinstitute.common.toApiError
import com.elevenine.artinstitute.data.api.ArtApi
import com.elevenine.artinstitute.data.api.model.request.GetCategorizedArtworksRequest
import com.elevenine.artinstitute.di.IoDispatcher
import com.elevenine.artinstitute.domain.mapper.ArtworkDtoUiMapper
import com.elevenine.artinstitute.domain.mapper.base.ListMapperImpl
import com.elevenine.artinstitute.domain.model.DataListPage
import com.elevenine.artinstitute.domain.repository.ArtResourceRepository
import com.elevenine.artinstitute.ui.model.Artwork
import com.elevenine.artinstitute.utils.tryCatchSafelySuspend
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * @author Sherzod Nosirov
 * @since 12.12.2021
 */

class ArtResourceRepositoryImpl @Inject constructor(
    private val artApi: ArtApi,
    private val artworkDtoUiMapperFactory: ArtworkDtoUiMapper.Factory,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ArtResourceRepository {

    override suspend fun fetchArtworkListPage(
        pageNumber: Int,
        pageSize: Int,
        categoryId: Long
    ): DataResult<DataListPage<Artwork>> {

        return withContext(ioDispatcher) {
            tryCatchSafelySuspend(tryBlock = suspend {
                val requestBody = GetCategorizedArtworksRequest.getInstance(categoryId)
                val result = artApi.getCategorizedArtworksByPage(requestBody, pageNumber, pageSize)

                val artworkDtoEntityMapper =
                    artworkDtoUiMapperFactory.create(result.config?.iiifUrl ?: "")

                val hasNextPage =
                    (result.pagination?.currentPage
                        ?: Int.MAX_VALUE) < (result.pagination?.totalPages ?: Int.MIN_VALUE)

                DataResult.Success(
                    DataListPage(
                        hasNextPage,
                        ListMapperImpl(artworkDtoEntityMapper).map(result.data)
                    )
                )
            }, catchBlock = { e ->
                DataResult.Error(e.toApiError())
            })
        }
    }
}