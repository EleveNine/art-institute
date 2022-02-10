package com.elevenine.artinstitute.di

import com.elevenine.artinstitute.data.repository.ArtResourceRepositoryImpl
import com.elevenine.artinstitute.data.repository.CategoryRepositoryImpl
import com.elevenine.artinstitute.domain.repository.ArtResourceRepository
import com.elevenine.artinstitute.domain.repository.CategoryRepository
import dagger.Binds
import dagger.Module

/**
 * @author Sherzod Nosirov
 * @since July 29, 2021
 */

@Module
interface RepositoryBindModule {

    @Binds
    fun bindArtResourceRepository(repo: ArtResourceRepositoryImpl): ArtResourceRepository

    @Binds
    fun bindCategoryRepository(repo: CategoryRepositoryImpl): CategoryRepository
}