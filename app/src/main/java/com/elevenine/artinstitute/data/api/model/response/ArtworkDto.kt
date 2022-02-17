package com.elevenine.artinstitute.data.api.model.response

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
class ArtworkDto(
    var id: Int?,
    @Json(name = "image_id")
    var imageId: String?,
    var title: String?,
    @Json(name = "main_reference_number")
    var mainReferenceNumber: String?,
    @Json(name = "date_display")
    var dateDisplay: String?,
    @Json(name = "artist_display")
    var artistDisplay: String?,
    @Json(name = "artist_id")
    var artistId: Long?,
    @Json(name = "artwork_type_id")
    var artworkTypeId: String?,
    @Json(name = "artwork_type_title")
    var artworkTypeTitle: String?
)