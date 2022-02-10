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
    fun bindRequestNewArtworkPageUseCase(impl: RequestAndCacheNewArtworkPageUseCaseImpl): RequestAndCacheNewArtworkPageUseCase

    @Binds
    fun bindGetCachedArtworksFlowUseCase(impl: GetCachedArtworksFlowUseCaseImpl): GetCachedArtworksFlowUseCase

    @Binds
    fun bindFetchCategoriesUseCase(impl: FetchCategoriesUseCaseImpl): FetchCategoriesAndCacheUseCase

    @Binds
    fun bindGetCachedCategoriesFlowUseCase(impl: GetCachedCategoriesFlowUseCaseImpl): GetCachedCategoriesFlowUseCase
}