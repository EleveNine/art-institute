package com.elevenine.artinstitute.data.api.model.response

import kotlinx.serialization.Serializable

@Serializable
data class DefaultErrorResponse(
    var status: Int? = null,
    var error: String? = null,
    var detail: String? = null
)