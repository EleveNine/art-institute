package com.elevenine.artinstitute.data.common

import com.elevenine.artinstitute.data.api.model.response.DefaultErrorResponse
import org.json.JSONObject

/**
 * @author Sherzod Nosirov
 * @since 08.12.2021
 */

/**
 * Class that realises 'Command' pattern to perform Http ErrorBody parsing.
 */
class ErrorBodyParser {

    /**
     * Parses the provided Http ErrorBody into [DefaultErrorResponse] object.
     *
     * @param body ErrorBody in string type to parse.
     *
     * @return an instance of [DefaultErrorResponse] that contains the parsed data.
     */
    operator fun invoke(body: String?): DefaultErrorResponse {
        val defaultErrorResponse = DefaultErrorResponse()

        if (body == null) return defaultErrorResponse

        val jsonObject = JSONObject(body)

        with(defaultErrorResponse) {
            status = jsonObject.optInt("status")
            error = jsonObject.optString("error")
            detail = jsonObject.optString("detail")
        }

        return defaultErrorResponse
    }
}