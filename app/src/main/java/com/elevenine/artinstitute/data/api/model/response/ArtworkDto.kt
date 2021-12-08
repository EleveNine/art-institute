package com.elevenine.artinstitute.data.api.model.response

import com.squareup.moshi.Json

class ArtworkDto(
    var id: Int?,
    var title: String?,
    @Json(name = "main_reference_number")
    var mainReferenceNumber: String?,
    @Json(name = "date_display")
    var dateDisplay: String?,
    @Json(name = "artist_display")
    var artistDisplay: String?
)