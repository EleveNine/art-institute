package com.elevenine.artinstitute.domain.use_case

import com.elevenine.artinstitute.common.DomainResult
import com.elevenine.artinstitute.ui.model.Category
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SyncAndGetCategoriesFlowUseCaseImpl @Inject constructor(
    private val syncCategoriesUseCase: SyncCategoriesUseCase,
    private val getCategoriesFlowUseCase: GetCachedCategoriesFlowUseCase
) : SyncAndGetCategoriesFlowUseCase {

    override suspend fun invoke(): DomainResult<Flow<List<Category>>> {
        val syncResult = syncCategoriesUseCase()

        if (syncResult is DomainResult.Error) return DomainResult.Error(syncResult.error)

        return getCategoriesFlowUseCase()
    }
}