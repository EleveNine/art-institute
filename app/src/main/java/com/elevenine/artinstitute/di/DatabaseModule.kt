package com.elevenine.artinstitute.di

import android.content.Context
import androidx.room.Room
import com.elevenine.artinstitute.data.database.AppDatabase
import com.elevenine.artinstitute.data.database.dao.ArtworkDao
import com.elevenine.artinstitute.data.database.dao.CategoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room
            .databaseBuilder(context, AppDatabase::class.java, "art_database.db")
            .build()
    }

    @Provides
    @Singleton
    fun provideArtworkDao(appDatabase: AppDatabase): ArtworkDao = appDatabase.artworkDao()

    @Provides
    @Singleton
    fun provideCategoriesDao(appDatabase: AppDatabase): CategoryDao = appDatabase.categoryDao()
}