package com.elevenine.artinstitute.data.repository

import com.elevenine.artinstitute.data.api.ArtApi
import com.elevenine.artinstitute.data.api.model.response.CategoryDto
import com.elevenine.artinstitute.data.common.DataResult
import com.elevenine.artinstitute.data.common.toApiError
import com.elevenine.artinstitute.data.common.toDatabaseError
import com.elevenine.artinstitute.data.database.dao.CategoryDao
import com.elevenine.artinstitute.data.database.entity.CategoryEntity
import com.elevenine.artinstitute.domain.repository.CategoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import uz.uzex.uzexmoney.di.IoDispatcher
import javax.inject.Inject

/**
 * @author Sherzod Nosirov
 * @since 05.02.2022
 */

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao,
    private val artApi: ArtApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : CategoryRepository {

    override suspend fun fetchCategories(): DataResult<List<CategoryDto>> {
        return withContext(ioDispatcher) {
            try {
                val result = artApi.getArtworkTypes()

                return@withContext DataResult.Success(result.data)
            } catch (e: Exception) {
                return@withContext DataResult.Error(e.toApiError())
            }
        }
    }

    override suspend fun cacheCategories(categories: List<CategoryEntity>): DataResult<Unit> {
        return withContext(ioDispatcher) {
            try {
                categoryDao.insertCategories(categories)

                return@withContext DataResult.Success(Unit)
            } catch (e: Exception) {
                return@withContext DataResult.Error(e.toApiError())
            }
        }
    }

    override suspend fun getCachedCategoriesFlow(): DataResult<Flow<List<CategoryEntity>>> {
        return withContext(ioDispatcher) {
            try {
                val flow = categoryDao.getCategoriesFlow()
                return@withContext DataResult.Success(flow)
            } catch (e: Exception) {
                return@withContext DataResult.Error(e.toDatabaseError())
            }
        }
    }
}