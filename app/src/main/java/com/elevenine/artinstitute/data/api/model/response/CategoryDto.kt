package com.elevenine.artinstitute.data.api.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

class CategoryDto(
    var id: Int?,
    @Json(name = "api_model")
    var apiModel: String?,
    @Json(name = "api_link")
    var apiLink: String?,
    var title: String?,
    @Json(name = "last_updated_source")
    var lastUpdatedSource: String?,
    @Json(name = "last_updated")
    var lastUpdated: String?,
    var timestamp: String?
)