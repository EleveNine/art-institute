package com.elevenine.artinstitute.data.common

import androidx.annotation.StringRes
import com.elevenine.artinstitute.R
import com.elevenine.artinstitute.data.api.model.response.DefaultErrorResponse
import org.json.JSONObject
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * @author Sherzod Nosirov
 * @since 03.11.2021
 */

/**
 * Function that forms an instance of either of the [BaseError]'s children: [ApiError],
 * [ServerError]. Internally it examines the provided [Exception] instance
 * and in case it is an [HttpException], returns the respective [BaseError] object based on the
 * status code of the errorBody. In case if the provided [Exception] instance is not an
 * [HttpException] this function returns an [UnknownError] object.
 *
 * @param errorTitleResId optional custom resId of the error title to be fed to the [BaseError]
 * children.
 *
 * @return an instance of [BaseError] that acts as the wrapper for the occurred exception.
 */
fun Exception.toApiError(@StringRes errorTitleResId: Int = R.string.error_http): BaseError {

    if (this is UnknownHostException || this is ConnectException || this is SocketTimeoutException) {
        return NoConnectionError(this, errorTitleResId = R.string.error_unable_to_connect)
    }

    if (this !is HttpException)
        return UnknownError(
            this,
            errorTitleId = R.string.error_unknown_http,
            errorMessage = this.message
        )

    val parser = ParseHttpErrorCommand()
    val errorResponse = parser(this.response()?.errorBody()?.string())

    return when {
        this.code() / 400 == 1 -> {
            ApiError(
                this,
                errorTitleResId,
                errorMessage = errorResponse.error ?: errorResponse.detail
            )
        }
        this.code() == 500 -> {
            ServerError(
                this,
                R.string.error_critical_server_connection,
                errorMessage = errorResponse.error ?: errorResponse.detail
            )
        }
        else -> UnknownError(
            this,
            errorTitleId = R.string.error_unknown_http,
            errorMessage = this.message
        )
    }
}

/**
 * Function that forms a wrapper for the exceptions occurred during the Database operations.
 *
 * @param errorTitleResId optional custom resId of the error title to be fed to the [DatabaseError].
 *
 * @return an instance of [DatabaseError].
 */
fun Exception.toDatabaseError(@StringRes errorTitleResId: Int = R.string.error_generic_database): DatabaseError {
    return DatabaseError(this, errorTitleResId)
}


/**
 * Class that realises 'Command' pattern to perform Http ErrorBody parsing.
 */
class ParseHttpErrorCommand {

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
            status = jsonObject.getInt("status")
            error = jsonObject.getString("error")
            detail = jsonObject.getString("detail")
        }

        return defaultErrorResponse
    }
}