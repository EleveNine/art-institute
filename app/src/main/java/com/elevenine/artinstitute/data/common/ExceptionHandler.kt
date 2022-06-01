package com.elevenine.artinstitute.data.common

import androidx.annotation.StringRes
import com.elevenine.artinstitute.R
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * @author Sherzod Nosirov
 * @since 03.11.2021
 */

/**
 * Function that forms an instance of either of the [BaseError]'s sub-classes: [ApiError],
 * [NoConnectionError], [UnknownError] or [ServerError].
 * Internally it examines the provided [Exception] instance and in case it is an [HttpException],
 * returns either an [ApiError] or [ServerError] object based on the status code of the errorBody.
 * In case if the provided [Exception] instance is either a [UnknownHostException] or
 * [ConnectException] or [SocketTimeoutException] this function returns an [NoConnectionError]
 * object.
 * In all other cases this function returns an instance of [UnknownError].
 *
 * @param errorTitleResId optional custom resId of the error title to be fed to the [BaseError]
 * children.
 *
 * @return an instance of [BaseError] that acts as the wrapper for the occurred exception.
 */
fun Exception.toApiError(
    @StringRes errorTitleResId: Int = R.string.error_http,
): BaseError {

    // check if the exception occurred indicates the connection problems
    if (this is UnknownHostException || this is ConnectException || this is SocketTimeoutException) {
        return NoConnectionError(this, fallbackMessageId = R.string.error_unableToConnect)
    }

    // if the exception is not an instance of the HttpException return an instance of UnknownError
    if (this !is HttpException)
        return UnknownError(
            this,
            fallbackMessageId = R.string.error_unknownHttp,
            errorMessage = this.message
        )

    // parse the errorBody stored in this instance of HttpException.
    val parser = ErrorBodyParser()
    val errorResponse =
        parser(this.response()?.errorBody()?.string())
    val errorMessage = errorResponse.error

    return when {
        // for 401 return the UnauthorizedError that indicates that the user is not authorized in
        // the system
        this.code() == 401 -> {
            UnauthorizedError(
                this,
                errorMessage,
                errorTitleResId,
            )
        }
        // for other 4xx return the ApiError
        this.code() / 400 == 1 -> {
            ClientError(
                this,
                errorMessage,
                errorTitleResId,
            )
        }
        // for 5xx return the ServerError
        this.code() == 500 -> {
            ServerError(
                this,
                R.string.error_criticalServerConnection,
                errorMessage = errorMessage
            )
        }
        else -> UnknownError(
            this,
            fallbackMessageId = R.string.error_unknownHttp,
            errorMessage = this.message
        )
    }
}

/**
 * Function that forms a wrapper for the exceptions occurred during the Room Database operations.
 *
 * @param errorTitleResId optional custom resId of the error title to be fed to the [DatabaseError].
 *
 * @return an instance of [DatabaseError].
 */
fun Exception.toDatabaseError(@StringRes errorTitleResId: Int = R.string.error_genericDatabase): DatabaseError {

    return DatabaseError(this, errorTitleResId)
}

fun Exception.toProcessError(@StringRes errorTitleResId: Int = R.string.error_processing): ProcessError {

    return ProcessError(this, errorTitleResId)
}