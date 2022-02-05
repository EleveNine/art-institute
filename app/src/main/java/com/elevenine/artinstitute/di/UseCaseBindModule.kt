package com.elevenine.artinstitute.di

import com.elevenine.artinstitute.domain.use_case.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * @author Sherzod Nosirov
 * @since August 1, 2021
 */

@Module
@InstallIn(SingletonComponent::class)
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