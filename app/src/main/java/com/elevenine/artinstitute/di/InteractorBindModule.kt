package com.elevenine.artinstitute.di

import com.elevenine.artinstitute.domain.interactor.FetchPagedArtworksInteractor
import com.elevenine.artinstitute.domain.interactor.FetchPagedArtworksInteractorImpl
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
}