package com.elevenine.artinstitute.data.repository

import com.elevenine.artinstitute.data.api.ArtApi
import com.elevenine.artinstitute.data.common.DataResult
import com.elevenine.artinstitute.data.common.toApiError
import com.elevenine.artinstitute.data.common.toDatabaseError
import com.elevenine.artinstitute.data.database.dao.ArtworkDao
import com.elevenine.artinstitute.data.database.entity.ArtworkEntity
import com.elevenine.artinstitute.domain.mapper.ArtworkDtoEntityMapper
import com.elevenine.artinstitute.domain.mapper.ArtworkEntityUiMapper
import com.elevenine.artinstitute.domain.mapper.base.ListMapperImpl
import com.elevenine.artinstitute.domain.repository.ArtResourceRepository
import com.elevenine.artinstitute.ui.model.Artwork
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import uz.uzex.uzexmoney.di.IoDispatcher
import javax.inject.Inject

/**
 * @author Sherzod Nosirov
 * @since 12.12.2021
 */

class ArtResourceRepositoryImpl @Inject constructor(
    private val artApi: ArtApi,
    private val artworkDao: ArtworkDao,
    private val artworkEntityUiMapper: ArtworkEntityUiMapper,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ArtResourceRepository {

    override suspend fun fetchArtworkListPage(pageNumber: Int): DataResult<List<ArtworkEntity>> {

        return withContext(ioDispatcher) {
            try {
                val result = artApi.getArtworksByPage(pageNumber)

                val artworkDtoEntityMapper = ArtworkDtoEntityMapper(result.config.iiifUrl ?: "")

                return@withContext DataResult.OnSuccess(
                    ListMapperImpl(artworkDtoEntityMapper).map(result.data)
                )
            } catch (e: Exception) {
                return@withContext DataResult.OnError(e.toApiError())
            }
        }
    }

    override suspend fun cacheArtworks(artworkList: List<ArtworkEntity>): DataResult<Unit> {
        return withContext(ioDispatcher) {
            try {
                artworkDao.insertArtworks(artworkList)
                return@withContext DataResult.OnSuccess(Unit)
            } catch (e: Exception) {
                return@withContext DataResult.OnError(e.toDatabaseError())
            }
        }
    }

    override suspend fun getCachedArtworks(): DataResult<List<Artwork>> {
        return withContext(ioDispatcher) {
            try {
                val list = artworkDao.getArtworks()
                return@withContext DataResult.OnSuccess(
                    ListMapperImpl(artworkEntityUiMapper).map(list)
                )
            } catch (e: Exception) {
                return@withContext DataResult.OnError(e.toDatabaseError())
            }
        }
    }
}