package com.elevenine.artinstitute.domain.interactor

import com.elevenine.artinstitute.domain.DomainState
import com.elevenine.artinstitute.ui.model.Category
import kotlinx.coroutines.flow.StateFlow

/**
 * @author Sherzod Nosirov
 * @since 05.02.2022
 */

interface GetCategoriesInteractor {
    val categoriesFlow: StateFlow<DomainState<List<Category>>>

    suspend fun initInteractor()

    suspend fun updateCategories()
}