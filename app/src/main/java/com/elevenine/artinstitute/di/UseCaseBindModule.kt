package com.elevenine.artinstitute.di

import com.elevenine.artinstitute.domain.use_case.*
import dagger.Binds
import dagger.Module

/**
 * @author Sherzod Nosirov
 * @since August 1, 2021
 */

@Module
interface UseCaseBindModule {

    @Binds
    fun bindRequestNewArtworkPageUseCase(impl: GetNextArtworkPageUseCaseImpl): GetNextArtworkPageUseCase

    @Binds
    fun bindFetchCategoriesUseCase(impl: SyncCategoriesUseCaseImpl): SyncCategoriesUseCase

    @Binds
    fun bindGetCachedCategoriesFlowUseCase(impl: GetCachedCategoriesFlowUseCaseImpl): GetCachedCategoriesFlowUseCase

    @Binds
    fun bindSyncAndGetCategoriesFlowUseCase(impl: SyncAndGetCategoriesFlowUseCaseImpl): SyncAndGetCategoriesFlowUseCase
}