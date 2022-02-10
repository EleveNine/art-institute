package com.elevenine.artinstitute.di

import com.elevenine.artinstitute.domain.interactor.FetchPagedArtworksInteractor
import com.elevenine.artinstitute.domain.interactor.FetchPagedArtworksInteractorImpl
import com.elevenine.artinstitute.domain.interactor.GetCategoriesInteractor
import com.elevenine.artinstitute.domain.interactor.GetCategoriesInteractorImpl
import dagger.Binds
import dagger.Module

/**
 * @author Sherzod Nosirov
 * @since 02.02.2022
 */

@Module
interface InteractorBindModule {
    @Binds
    fun bindFetchPagedArtworksInteractor(impl: FetchPagedArtworksInteractorImpl): FetchPagedArtworksInteractor

    @Binds
    fun bindGetCategoriesInteractor(impl: GetCategoriesInteractorImpl): GetCategoriesInteractor
}