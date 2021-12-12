package com.elevenine.artinstitute.di

import com.elevenine.artinstitute.data.repository.ArtResourceRepositoryImpl
import com.elevenine.artinstitute.domain.repository.ArtResourceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * @author Sherzod Nosirov
 * @since July 29, 2021
 */

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryBindModule {

    @Binds
    fun bindArtResourceRepository(repo: ArtResourceRepositoryImpl): ArtResourceRepository
}