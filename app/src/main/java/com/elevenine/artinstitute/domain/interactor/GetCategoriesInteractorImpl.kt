package com.elevenine.artinstitute.domain.interactor

import com.elevenine.artinstitute.data.common.DataResult
import com.elevenine.artinstitute.domain.DomainState
import com.elevenine.artinstitute.domain.use_case.FetchCategoriesAndCacheUseCase
import com.elevenine.artinstitute.domain.use_case.GetCachedCategoriesFlowUseCase
import com.elevenine.artinstitute.ui.model.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

/**
 * @author Sherzod Nosirov
 * @since 05.02.2022
 */

class GetCategoriesInteractorImpl @Inject constructor(
    private val fetchCategoriesAndCacheUseCase: FetchCategoriesAndCacheUseCase,
    private val getCachedCategoriesFlowUseCase: GetCachedCategoriesFlowUseCase
) : GetCategoriesInteractor {

    override val categoriesFlow: StateFlow<DomainState<List<Category>>>
        get() = _categoriesFlow.asStateFlow()

    private val _categoriesFlow =
        MutableStateFlow<DomainState<List<Category>>>(DomainState.Loading())

    private var categoriesList = listOf<Category>()

    override suspend fun initInteractor() {
        val flow = getCachedCategoriesFlowUseCase()

        flow.collect { categories ->
            categoriesList = categories
            _categoriesFlow.value = DomainState.Success(categoriesList)
        }
    }

    override suspend fun updateCategories() {
        val result = fetchCategoriesAndCacheUseCase()
        if (result is DataResult.Error) {
            _categoriesFlow.value = DomainState.Error(result.error, categoriesList)
        }
    }
}