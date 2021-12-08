package com.elevenine.artinstitute.di

import android.content.Context
import androidx.room.Room
import com.elevenine.artinstitute.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@AppContext context: Context): AppDatabase {
        return Room
            .databaseBuilder(context, AppDatabase::class.java, "art_database.db")
            .build()
    }
}