package com.elevenine.artinstitute.di

import android.content.Context
import androidx.room.Room
import com.elevenine.artinstitute.data.database.AppDatabase
import com.elevenine.artinstitute.data.database.dao.ArtworkDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@AppContext context: Context): AppDatabase {
        return Room
            .databaseBuilder(context, AppDatabase::class.java, "art_database.db")
            .build()
    }

    @Provides
    @Singleton
    fun provideArtworkDao(appDatabase: AppDatabase): ArtworkDao = appDatabase.artworkDao()
}