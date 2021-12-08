package com.elevenine.artinstitute.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Sherzod Nosirov
 * @since 08.12.2021
 */

@Entity(tableName = "artworks")
class ArtworkEntity(
    @PrimaryKey val id: Long
)