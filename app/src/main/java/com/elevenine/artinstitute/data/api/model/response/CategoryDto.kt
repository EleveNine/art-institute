package com.elevenine.artinstitute.data.api.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    var id: Int? = null,
    @SerialName("api_model")
    var apiModel: String? = null,
    @SerialName("api_link")
    var apiLink: String? = null,
    var title: String? = null,
    @SerialName("last_updated_source")
    var lastUpdatedSource: String? = null,
    @SerialName("last_updated")
    var lastUpdated: String? = null,
    var timestamp: String? = null
)