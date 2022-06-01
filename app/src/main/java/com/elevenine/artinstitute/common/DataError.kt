package com.elevenine.artinstitute.common

import androidx.annotation.StringRes
import com.elevenine.artinstitute.R
import retrofit2.HttpException

/**
 * @author Sherzod Nosirov
 * @since 16.02.2022
 */

/**
 * The base class for wrapping various errors that may occur in the data-layer of the app. This
 * class encapsulates an exception occurred in the data-layer, as well as some additional info.
 *
 * @param internalException the exception occurred in the data-layer.
 *
 * @param fallbackMessageId fallback message resId in case there is not any provided by the data
 * layer into the [errorMessage] field.
 *
 * @param errorMessage optional extra string value to act as an error description. Usually it
 * contains a message obtained from API error response.
 */
abstract class BaseError(
    var internalException: Throwable,
    @StringRes var fallbackMessageId: Int,
    var errorMessage: String?,
) : Exception(internalException.message, internalException)


/**
 * An error wrapper class that represents an API error which was got from a 4XX status. These
 * statuses indicate an error received from the backend and must be handled with the Error State in
 * the UI. In most cases the error body of the API response contains an errorMessage that should be
 * shown in the UI.
 *
 * @param exception the instance of [HttpException] occurred in the data-layer.
 *
 * @param fallbackMessageId fallback message resId in case there is not any provided by the data
 * layer into the [errorMessage] object field.
 */
abstract class ApiError(
    exception: HttpException,
    errorMessage: String?,
    @StringRes fallbackMessageId: Int,
) : BaseError(
    exception,
    fallbackMessageId,
    errorMessage
)

class ClientError(
    exception: HttpException,
    errorMessage: String?,
    @StringRes fallbackMessageId: Int,
) : ApiError(exception, errorMessage, fallbackMessageId)

/**
 * An error wrapper class that represents an API error which was got from a 401 status. This
 * status code indicates an auth error when accessing the protected methods of the API. Receiving
 * this error means that the user is no longer authorized in the app and the automatic logout must
 * take place.
 * In most cases the error body of the API response contains an errorMessage that should be
 * shown in the UI.
 *
 * @param exception the instance of [HttpException] occurred in the data-layer.
 *
 * @param fallbackMessageId fallback message resId in case there is not any provided by the data
 * layer into the [errorMessage] object field.
 */
class UnauthorizedError(
    exception: HttpException,
    errorMessage: String?,
    @StringRes fallbackMessageId: Int,
) : ApiError(exception, errorMessage, fallbackMessageId)

/**
 * An error wrapper class that represents an API 5XX Server errors. This status may indicate the
 * problems with the server and must be handled accordingly. In some cases the
 * response body of the obtained from the API response may contain an errorMessage that should be
 * shown in the UI's Error State.
 *
 * @param exception the instance of [HttpException] occurred in the data-layer.
 *
 * @param fallbackMessageId fallback message resId in case there is not any provided by the data
 * layer into the [errorMessage] field.
 *
 * @param errorMessage optional extra string value to act as an error description. Usually it
 * contains a message obtained from API error response.
 */
class ServerError(
    exception: HttpException,
    @StringRes fallbackMessageId: Int,
    errorMessage: String? = null,
) : BaseError(
    exception,
    fallbackMessageId,
    errorMessage
)

/**
 * An error wrapper class that represents errors occurred with the connection to the internet.
 *
 * @param exception the instance of [Throwable] occurred in the data-layer.
 *
 * @param fallbackMessageId fallback message resId that describes the error.
 */
class NoConnectionError(
    exception: Throwable,
    @StringRes fallbackMessageId: Int,
) : BaseError(
    exception,
    fallbackMessageId,
    null
)

/**
 * An error wrapper class that represents errors occurred during accessing the Android Room.
 *
 * @param exception the instance of [Throwable] occurred in the data-layer.
 *
 * @param fallbackMessageId fallback message resId that describes the error.
 */
class DatabaseError(
    exception: Throwable,
    @StringRes fallbackMessageId: Int,
) : BaseError(
    exception,
    fallbackMessageId,
    null
)

/**
 * An error wrapper class that represents undefined errors during some data or business logic
 * operations.
 *
 * @param exception the instance of [Throwable] occurred in the data-layer.
 *
 * @param fallbackMessageId fallback message resId that describes the error.
 */
class UnknownError(
    exception: Throwable,
    @StringRes fallbackMessageId: Int,
    errorMessage: String? = null,
) : BaseError(
    exception,
    fallbackMessageId,
    errorMessage
)

/**
 * An error wrapper class that represents the cases when the empty data set was returned from the
 * data layer. This type of error may be especially useful when working with paginated data.
 *
 * @param exception the instance of [Throwable] occurred in the data-layer.
 *
 * @param errorMessageId error message resId that describes the error.
 */
class EmptyDataError(
    exception: Throwable = NullPointerException(),
    @StringRes errorMessageId: Int = R.string.error_emptyData,
) : BaseError(
    exception,
    errorMessageId,
    null
)

/**
 * An error wrapper class that represents processing errors that occurred during some operations in
 * either the domain or the data layers of the app.
 *
 * @param exception the instance of [Throwable] occurred in the data-layer.
 *
 * @param fallbackMessageId fallback message resId that describes the error.
 */
class ProcessError(
    exception: Throwable = IllegalStateException(),
    @StringRes fallbackMessageId: Int = R.string.error_processing,
) : BaseError(
    exception,
    fallbackMessageId,
    null
)