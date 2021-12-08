package com.elevenine.artinstitute.data.api.model.response

class DefaultErrorResponse(
    var status: Int? = null,
    var error: String? = null,
    var detail: String? = null
)