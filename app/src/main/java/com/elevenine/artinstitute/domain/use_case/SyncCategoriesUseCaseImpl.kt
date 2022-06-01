package com.elevenine.artinstitute.domain.use_case

import com.elevenine.artinstitute.common.toDomainResult
import com.elevenine.artinstitute.common.DomainResult
import com.elevenine.artinstitute.domain.mapper.CategoryDtoEntityMapper
import com.elevenine.artinstitute.domain.mapper.base.ListMapperImpl
import com.elevenine.artinstitute.domain.repository.CategoryRepository
import javax.inject.Inject

/**
 * @author Sherzod Nosirov
 * @since 05.02.2022
 */

class SyncCategoriesUseCaseImpl @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val categoryDtoEntityMapper: CategoryDtoEntityMapper
) : SyncCategoriesUseCase {

    override suspend fun invoke(): DomainResult<Unit> {
        val apiResult = categoryRepository.fetchCategories().toDomainResult()

        return when (apiResult) {
            is DomainResult.Error -> apiResult
            is DomainResult.Success -> {
                val mappedList = ListMapperImpl(categoryDtoEntityMapper).map(apiResult.data)

                categoryRepository.cacheCategories(mappedList).toDomainResult()
            }
        }
    }
}