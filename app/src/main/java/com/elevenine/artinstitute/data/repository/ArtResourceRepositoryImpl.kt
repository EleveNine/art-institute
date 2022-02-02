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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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
    private val artworkDtoEntityMapperFactory: ArtworkDtoEntityMapper.Factory,
    private val artworkEntityUiMapper: ArtworkEntityUiMapper,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ArtResourceRepository {

    companion object {
        const val PAGE_SIZE_LIMIT = 25
    }

    override suspend fun fetchArtworkListPage(pageNumber: Int): DataResult<List<ArtworkEntity>> {

        return withContext(ioDispatcher) {
            try {
                val result = artApi.getArtworksByPage(pageNumber, PAGE_SIZE_LIMIT)

                //todo check if the last page is reached -> return DataResult.OnError with EmptyDataError

                val artworkDtoEntityMapper = artworkDtoEntityMapperFactory.create(result.config.iiifUrl ?: "")

                return@withContext DataResult.Success(
                    ListMapperImpl(artworkDtoEntityMapper).map(result.data)
                )
            } catch (e: Exception) {
                return@withContext DataResult.Error(e.toApiError())
            }
        }
    }

    override suspend fun cacheArtworks(artworkList: List<ArtworkEntity>): DataResult<Unit> {
        return withContext(ioDispatcher) {
            try {
                artworkDao.insertArtworks(artworkList)
                return@withContext DataResult.Success(Unit)
            } catch (e: Exception) {
                return@withContext DataResult.Error(e.toDatabaseError())
            }
        }
    }

    override suspend fun getCachedArtworks(): DataResult<List<Artwork>> {
        return withContext(ioDispatcher) {
            try {
                val list = artworkDao.getArtworks()
                return@withContext DataResult.Success(
                    ListMapperImpl(artworkEntityUiMapper).map(list)
                )
            } catch (e: Exception) {
                return@withContext DataResult.Error(e.toDatabaseError())
            }
        }
    }

    override suspend fun getCachedArtworksFlow(): DataResult<Flow<List<Artwork>>> {
        return withContext(ioDispatcher) {
            try {
                val flow = artworkDao.getArtworksFlow()
                return@withContext DataResult.Success(
                    flow.map { list ->
                        ListMapperImpl(artworkEntityUiMapper).map(list)
                    }
                )
            } catch (e: Exception) {
                return@withContext DataResult.Error(e.toDatabaseError())
            }
        }
    }
}