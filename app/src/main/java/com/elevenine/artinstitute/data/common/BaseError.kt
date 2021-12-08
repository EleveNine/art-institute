package com.elevenine.artinstitute.data.common

import androidx.annotation.StringRes
import com.elevenine.artinstitute.R
import com.elevenine.artinstitute.data.api.model.response.DefaultErrorResponse
import retrofit2.HttpException

/**
 * @author Sherzod Nosirov
 * @since July 30, 2021
 */

/**
 * The base class for wrapping various errors that may occur in the data-layer of the app. This
 * class encapsulates the exception occurred in the data-layer, as well as additional info.
 *
 * @param internalException the exception occurred in the data-layer.
 *
 * @param errorTitleId the resource ID of the title of the error that relates to the occurred
 * exception. This resId is used in an ExceptionHandler and is shown as a primary error message in
 * the UI.
 *
 * @param errorMessageId optional extra errorMessage resId.
 *
 * @param errorMessage optional extra string value to act as an error description. Usually it
 * contains a message obtained from [DefaultErrorResponse] parsed by [ErrorBodyParser].
 */
open class BaseError(
    var internalException: Throwable,
    @StringRes var errorTitleId: Int,
    @StringRes var errorMessageId: Int?,
    var errorMessage: String?,
) : Exception(internalException.message, internalException)


/**
 * An error wrapper class that represents an Api error which was got from 4** status. This statuses
 * mean an error received from the backend and must be handled with the Error State in the
 * UI. In most cases the [DefaultErrorResponse] body of the obtained from the API response contains
 * an errorMessage that should be shown in the ErrorDialog in addition to the [errorTitleId].
 *
 * @param exception the instance of [HttpException] occurred in the data-layer.
 *
 * @param errorTitleResId the resource ID of the title of the error that relates to the occurred
 * exception. This resId is used in an ExceptionHandler and is shown as a primary error message in
 * the UI.
 *
 * @param errorMessageId optional extra errorMessage resId.
 *
 * @param errorMessage optional extra string value to act as an error description. Usually it
 * contains a message obtained from [DefaultErrorResponse] parsed by [ErrorBodyParser].
 */
class ApiError(
    exception: HttpException,
    @StringRes errorTitleResId: Int = R.string.error_http,
    @StringRes errorMessageId: Int? = null,
    errorMessage: String? = null,
) : BaseError(
    exception,
    errorTitleId = errorTitleResId,
    errorMessageId = errorMessageId,
    errorMessage = errorMessage
)

/**
 * An error wrapper class that represents an Api 5** Server errors. This status may indicate the
 * problems with the server and must be handled accordingly. In some cases the
 * [DefaultErrorResponse] body of the obtained from the API response may contain
 * an errorMessage that should be shown in the Error State in addition to the [errorTitleId].
 *
 * @param exception the instance of [HttpException] occurred in the data-layer.
 *
 * @param errorTitleResId the resource ID of the title of the error that relates to the occurred
 * exception. This resId is used in an ExceptionHandler and is shown as a primary error message in
 * the UI.
 *
 * @param errorMessageId optional extra errorMessage resId.
 *
 * @param errorMessage optional extra string value to act as an error description. Usually it
 * contains a message obtained from [DefaultErrorResponse] parsed by [ErrorBodyParser].
 */
class ServerError(
    exception: HttpException,
    @StringRes errorTitleResId: Int = R.string.error_http,
    @StringRes errorMessageId: Int? = null,
    errorMessage: String? = null,
) : BaseError(
    exception,
    errorTitleId = errorTitleResId,
    errorMessageId = errorMessageId,
    errorMessage = errorMessage
)


class NoConnectionError(
    exception: Exception,
    @StringRes errorTitleResId: Int = R.string.error_http,
    @StringRes errorMessageId: Int? = null,
    errorMessage: String? = null,
) : BaseError(
    exception,
    errorTitleId = errorTitleResId,
    errorMessageId = errorMessageId,
    errorMessage = errorMessage
)


class DatabaseError(
    exception: Throwable,
    @StringRes errorTitleResId: Int? = null
) : BaseError(
    exception,
    errorTitleId = errorTitleResId ?: R.string.error_generic_database,
    errorMessageId = null,
    errorMessage = null
)


class UnknownError(
    exception: Throwable,
    @StringRes errorTitleId: Int? = null,
    errorMessage: String? = null,
) : BaseError(
    exception,
    errorTitleId = errorTitleId ?: R.string.error_unknown,
    errorMessageId = null,
    errorMessage = errorMessage
)


class EmptyDataError(
    exception: Throwable,
    @StringRes errorTitleId: Int? = null,
    errorMessage: String? = null,
) : BaseError(
    exception,
    errorTitleId = errorTitleId ?: R.string.error_empty_data,
    errorMessageId = null,
    errorMessage = errorMessage
)