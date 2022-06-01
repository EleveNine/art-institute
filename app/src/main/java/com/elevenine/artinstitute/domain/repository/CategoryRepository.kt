package com.elevenine.artinstitute.domain.repository

import com.elevenine.artinstitute.data.api.model.response.CategoryDto
import com.elevenine.artinstitute.common.DataResult
import com.elevenine.artinstitute.data.database.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

/**
 * @author Sherzod Nosirov
 * @since 05.02.2022
 */

interface CategoryRepository {

    suspend fun fetchCategories(): DataResult<List<CategoryDto>>

    suspend fun cacheCategories(categories: List<CategoryEntity>): DataResult<Unit>

    suspend fun getCachedCategoriesFlow(): DataResult<Flow<List<CategoryEntity>>>
}