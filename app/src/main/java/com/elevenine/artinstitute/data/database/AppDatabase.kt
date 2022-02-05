package com.elevenine.artinstitute.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.elevenine.artinstitute.data.database.dao.ArtworkDao
import com.elevenine.artinstitute.data.database.dao.CategoryDao
import com.elevenine.artinstitute.data.database.entity.ArtworkEntity
import com.elevenine.artinstitute.data.database.entity.CategoryEntity

/**
 * @author Sherzod Nosirov
 * @since 08.12.2021
 */

@Database(
    entities = [
        ArtworkEntity::class,
        CategoryEntity::class
    ], version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun artworkDao(): ArtworkDao

    abstract fun categoryDao(): CategoryDao
}