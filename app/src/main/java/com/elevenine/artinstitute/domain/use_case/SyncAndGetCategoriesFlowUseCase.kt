package com.elevenine.artinstitute.domain.use_case

import com.elevenine.artinstitute.domain.DomainResult
import com.elevenine.artinstitute.ui.model.Category
import kotlinx.coroutines.flow.Flow

interface SyncAndGetCategoriesFlowUseCase {

    suspend operator fun invoke(): DomainResult<Flow<List<Category>>>
}