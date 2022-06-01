package com.elevenine.artinstitute.domain.use_case

import com.elevenine.artinstitute.common.DomainResult
import com.elevenine.artinstitute.ui.model.Category
import kotlinx.coroutines.flow.Flow

/**
 * @author Sherzod Nosirov
 * @since 05.02.2022
 */

interface GetCachedCategoriesFlowUseCase {
    suspend operator fun invoke(): DomainResult<Flow<List<Category>>>
}