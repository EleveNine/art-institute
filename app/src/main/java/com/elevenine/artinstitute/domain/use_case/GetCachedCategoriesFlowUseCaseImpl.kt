package com.elevenine.artinstitute.domain.use_case

import com.elevenine.artinstitute.data.common.DataResult
import com.elevenine.artinstitute.domain.mapper.CategoryEntityUiMapper
import com.elevenine.artinstitute.domain.mapper.base.ListMapperImpl
import com.elevenine.artinstitute.domain.repository.CategoryRepository
import com.elevenine.artinstitute.ui.model.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @author Sherzod Nosirov
 * @since 05.02.2022
 */

class GetCachedCategoriesFlowUseCaseImpl @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val categoryEntityUiMapper: CategoryEntityUiMapper
) : GetCachedCategoriesFlowUseCase {
    override suspend fun invoke(): Flow<List<Category>> {

        return when (val result = categoryRepository.getCachedCategoriesFlow()) {
            is DataResult.Error -> flowOf()
            is DataResult.Success -> {
                result.data.map { categories ->
                    ListMapperImpl(categoryEntityUiMapper).map(categories)
                }
            }
        }
    }
}