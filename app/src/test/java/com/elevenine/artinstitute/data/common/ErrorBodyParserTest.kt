package com.elevenine.artinstitute.data.common

import com.google.common.truth.Truth
import org.junit.Test

class ErrorBodyParserTest {
    val errorBodyParser = ErrorBodyParser()

    private val errorBody = "{\n" +
            "  \"status\": 404,\n" +
            "  \"error\": \"Sorry, something went wrong.\",\n" +
            "  \"detail\": \"An unrecognized exception was thrown. Our developers have been alerted to the situation.\"\n" +
            "}"

    @Test
    fun `should return empty object when error body is null`() {
        val defaultErrorResponse = errorBodyParser(null)

        Truth.assertThat(defaultErrorResponse.detail).isNull()
        Truth.assertThat(defaultErrorResponse.status).isNull()
        Truth.assertThat(defaultErrorResponse.error).isNull()
    }

    @Test
    fun `should fill error response object with proper values`() {

        val defaultErrorResponse = errorBodyParser(errorBody)

        Truth.assertThat(defaultErrorResponse.detail)
            .isEqualTo("An unrecognized exception was thrown. Our developers have been alerted to the situation.")
        Truth.assertThat(defaultErrorResponse.status).isEqualTo(404)
        Truth.assertThat(defaultErrorResponse.error).isEqualTo("Sorry, something went wrong.")
    }
}