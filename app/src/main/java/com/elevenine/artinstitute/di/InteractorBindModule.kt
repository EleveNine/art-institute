package com.elevenine.artinstitute.di

import com.elevenine.artinstitute.domain.interactor.FetchPagedArtworksInteractor
import com.elevenine.artinstitute.domain.interactor.FetchPagedArtworksInteractorImpl
import com.elevenine.artinstitute.domain.interactor.GetCategoriesInteractor
import com.elevenine.artinstitute.domain.interactor.GetCategoriesInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * @author Sherzod Nosirov
 * @since 02.02.2022
 */

@Module
@InstallIn(SingletonComponent::class)
interface InteractorBindModule {
    @Binds
    fun bindFetchPagedArtworksInteractor(impl: FetchPagedArtworksInteractorImpl): FetchPagedArtworksInteractor

    @Binds
    fun bindGetCategoriesInteractor(impl: GetCategoriesInteractorImpl): GetCategoriesInteractor
}