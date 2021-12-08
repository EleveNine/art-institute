package com.elevenine.artinstitute.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.elevenine.artinstitute.data.database.entity.ArtworkEntity

/**
 * @author Sherzod Nosirov
 * @since 08.12.2021
 */

@Database(
    entities = [
        ArtworkEntity::class
    ], version = 1
)
abstract class AppDatabase : RoomDatabase() {
}