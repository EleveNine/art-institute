package com.elevenine.artinstitute.common

import com.elevenine.artinstitute.data.api.model.response.DefaultErrorResponse
import kotlinx.serialization.json.Json
import org.json.JSONObject

/**
 * @author Sherzod Nosirov
 * @since 08.12.2021
 */

/**
 * Special command class that acts as a parser that decodes the JSON response received from the
 * remote API.
 */
class ErrorBodyParser {

    operator fun invoke(
        errorBody: String?,
    ): DefaultErrorResponse {
        return Json.decodeFromString(DefaultErrorResponse.serializer(), errorBody ?: "")
    }
}