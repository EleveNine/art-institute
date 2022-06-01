package com.elevenine.artinstitute.domain.use_case

import com.elevenine.artinstitute.common.toDomainResult
import com.elevenine.artinstitute.domain.DomainResult
import com.elevenine.artinstitute.domain.mapper.CategoryEntityUiMapper
import com.elevenine.artinstitute.domain.mapper.base.ListMapperImpl
import com.elevenine.artinstitute.domain.repository.CategoryRepository
import com.elevenine.artinstitute.ui.model.Category
import kotlinx.coroutines.flow.Flow
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
    override suspend fun invoke(): DomainResult<Flow<List<Category>>> {

        return when (val result = categoryRepository.getCachedCategoriesFlow().toDomainResult()) {
            is DomainResult.Error -> result
            is DomainResult.Success -> {
                val mappedFlow = result.data.map { categories ->
                    ListMapperImpl(categoryEntityUiMapper).map(categories)
                }

                DomainResult.Success(mappedFlow)
            }
        }
    }
}