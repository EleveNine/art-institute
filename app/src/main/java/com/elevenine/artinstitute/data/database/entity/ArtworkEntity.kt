package com.elevenine.artinstitute.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Sherzod Nosirov
 * @since 08.12.2021
 */

@Entity(tableName = "artworks")
class ArtworkEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "image_id")
    var imageId: String,
    var imageUrl: String,
    var title: String,
    @ColumnInfo(name = "main_reference_number")
    var mainReferenceNumber: String,
    @ColumnInfo(name = "date_display")
    var dateDisplay: String,
    @ColumnInfo(name = "artist_display")
    var artistDisplay: String,
    @ColumnInfo(name = "artist_id")
    var artistId: Long,
    @ColumnInfo(name = "artwork_type_id")
    var artworkTypeId: String,
    @ColumnInfo(name = "artwork_type_title")
    var artworkTypeTitle: String
)