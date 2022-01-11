package com.elevenine.artinstitute.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elevenine.artinstitute.data.database.entity.ArtworkEntity

/**
 * @author Sherzod Nosirov
 * @since 11.01.2022
 */

@Dao
interface ArtworkDao {

    @Insert(entity = ArtworkEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArtworks(artworkList: List<ArtworkEntity>)

    @Query(
        """
            SELECT * FROM artworks
        """
    )
    fun getArtworks(): List<ArtworkEntity>
}