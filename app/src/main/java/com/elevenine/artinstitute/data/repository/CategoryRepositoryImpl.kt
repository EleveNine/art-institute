package com.elevenine.artinstitute.data.repository

import com.elevenine.artinstitute.common.DataResult
import com.elevenine.artinstitute.common.toApiError
import com.elevenine.artinstitute.common.toDatabaseError
import com.elevenine.artinstitute.data.api.ArtApi
import com.elevenine.artinstitute.data.api.model.response.CategoryDto
import com.elevenine.artinstitute.data.database.dao.CategoryDao
import com.elevenine.artinstitute.data.database.entity.CategoryEntity
import com.elevenine.artinstitute.di.IoDispatcher
import com.elevenine.artinstitute.domain.repository.CategoryRepository
import com.elevenine.artinstitute.utils.tryCatchSafelySuspend
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
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
            tryCatchSafelySuspend(tryBlock = suspend {
                val result = artApi.getArtworkTypes()

                DataResult.Success(result.data)
            }, catchBlock = { e ->
                DataResult.Error(e.toApiError())
            })
        }
    }

    override suspend fun cacheCategories(categories: List<CategoryEntity>): DataResult<Unit> {
        return withContext(ioDispatcher) {
            tryCatchSafelySuspend(tryBlock = suspend {
                categoryDao.insertCategories(categories)

                DataResult.Success(Unit)
            }, catchBlock = { e ->
                DataResult.Error(e.toApiError())
            })
        }
    }

    override suspend fun getCachedCategoriesFlow(): DataResult<Flow<List<CategoryEntity>>> {
        return withContext(ioDispatcher) {
            tryCatchSafelySuspend(tryBlock = suspend {
                val flow = categoryDao.getCategoriesFlow()
                DataResult.Success(flow)
            }, catchBlock = { e ->
                DataResult.Error(e.toDatabaseError())
            })
        }
    }
}