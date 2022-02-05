package com.elevenine.artinstitute.domain.use_case

import com.elevenine.artinstitute.data.common.DataResult
import com.elevenine.artinstitute.domain.mapper.CategoryDtoEntityMapper
import com.elevenine.artinstitute.domain.mapper.base.ListMapperImpl
import com.elevenine.artinstitute.domain.repository.CategoryRepository
import javax.inject.Inject

/**
 * @author Sherzod Nosirov
 * @since 05.02.2022
 */

class FetchCategoriesUseCaseImpl @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val categoryDtoEntityMapper: CategoryDtoEntityMapper
) : FetchCategoriesAndCacheUseCase {

    override suspend fun invoke(): DataResult<Unit> {
        val apiResult = categoryRepository.fetchCategories()

        return when (apiResult) {
            is DataResult.Error -> apiResult
            is DataResult.Success -> {
                val mappedList = ListMapperImpl(categoryDtoEntityMapper).map(apiResult.data)

                categoryRepository.cacheCategories(mappedList)
            }
        }
    }
}