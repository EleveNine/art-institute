package com.elevenine.artinstitute.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Sherzod Nosirov
 * @since 05.02.2022
 */

@Entity(tableName = "categories")
class CategoryEntity(
    @PrimaryKey
    var id: Int,
    @ColumnInfo(name = "api_model")
    var apiModel: String,
    @ColumnInfo(name = "api_link")
    var apiLink: String,
    var title: String,
    @ColumnInfo(name = "last_updated")
    var lastUpdated: String?
)