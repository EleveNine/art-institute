package com.elevenine.artinstitute.data.repository

import com.elevenine.artinstitute.data.api.ArtApi
import com.elevenine.artinstitute.common.DataResult
import com.elevenine.artinstitute.common.toApiError
import com.elevenine.artinstitute.di.IoDispatcher
import com.elevenine.artinstitute.domain.mapper.ArtworkDtoUiMapper
import com.elevenine.artinstitute.domain.mapper.base.ListMapperImpl
import com.elevenine.artinstitute.domain.model.DataListPage
import com.elevenine.artinstitute.domain.repository.ArtResourceRepository
import com.elevenine.artinstitute.ui.model.Artwork
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
        pageSize: Int
    ): DataResult<DataListPage<Artwork>> {

        return withContext(ioDispatcher) {
            try {
                val result = artApi.getArtworksByPage(pageNumber, pageSize)

                val artworkDtoEntityMapper =
                    artworkDtoUiMapperFactory.create(result.config?.iiifUrl ?: "")

                return@withContext DataResult.Success(
                    DataListPage(
                        result.pagination?.nextUrl != null,
                        ListMapperImpl(artworkDtoEntityMapper).map(result.data)
                    )
                )
            } catch (e: Exception) {
                return@withContext DataResult.Error(e.toApiError())
            }
        }
    }
}