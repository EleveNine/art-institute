package com.elevenine.artinstitute.data.api.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    var id: Int?,
    @SerialName("api_model")
    var apiModel: String?,
    @SerialName("api_link")
    var apiLink: String?,
    var title: String?,
    @SerialName("last_updated_source")
    var lastUpdatedSource: String?,
    @SerialName("last_updated")
    var lastUpdated: String?,
    var timestamp: String?
)