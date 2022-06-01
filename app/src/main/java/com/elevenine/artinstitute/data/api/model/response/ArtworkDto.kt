package com.elevenine.artinstitute.data.api.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtworkDto(
    @SerialName("artist_display")
    val artistDisplay: String? = null,
    @SerialName("artist_id")
    val artistId: Long? = null,
    @SerialName("artwork_type_id")
    val artworkTypeId: Int? = null,
    @SerialName("artwork_type_title")
    val artworkTypeTitle: String? = null,
    @SerialName("date_display")
    val dateDisplay: String? = null,
    val id: Int? = null,
    @SerialName("image_id")
    val imageId: String? = null,
    @SerialName("main_reference_number")
    val mainReferenceNumber: String? = null,
    val title: String? = null
)