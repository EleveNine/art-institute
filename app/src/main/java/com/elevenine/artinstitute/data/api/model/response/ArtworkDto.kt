package com.elevenine.artinstitute.data.api.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtworkDto(
    var id: Int? = null,
    @SerialName("image_id")
    var imageId: String? = null,
    var title: String? = null,
    @SerialName("main_reference_number")
    var mainReferenceNumber: String? = null,
    @SerialName("date_display")
    var dateDisplay: String? = null,
    @SerialName("artist_display")
    var artistDisplay: String? = null,
    @SerialName("artist_id")
    var artistId: Long? = null,
    @SerialName("artwork_type_id")
    var artworkTypeId: String? = null,
    @SerialName("artwork_type_title")
    var artworkTypeTitle: String? = null
)