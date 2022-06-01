package com.elevenine.artinstitute.common

import com.elevenine.artinstitute.data.common.BaseError
import com.elevenine.artinstitute.data.common.DataResult
import com.elevenine.artinstitute.domain.DomainResult
import com.elevenine.artinstitute.utils.printStackTraceIfDebug


/**
 * Utility extension function to transform the provided [DataResult] to [DomainResult]. Use this
 * function when plain conversion is required.
 *
 * @return the instance of the [DomainResult].
 */
fun <T> DataResult<T>.toDomainResult(): DomainResult<T> {
    return when (this) {
        is DataResult.Error -> {

            DomainResult.Error(this.error)
        }
        is DataResult.Success -> DomainResult.Success(this.data)
    }
}

/**
 * Utility extension function to transform the provided [DataResult] to [DomainResult]. This is an
 * overloaded version of the [toDomainResult] function to provide custom handler for the cases when
 * [DataResult.Success] received.
 *
 * @param printStackTrace the flag that indicates if the stack trace is needed to be printed in
 * the logs in case [DataResult.Error] occurs.
 *
 * @param successHandler the block of code that must be invoked in case of the [DataResult.Success].
 *
 * @return the instance of the [DomainResult].
 */
fun <T, U> DataResult<T>.toDomainResult(
    printStackTrace: Boolean = true,
    successHandler: (T) -> DomainResult<U>
): DomainResult<U> {
    return when (this) {
        is DataResult.Error -> {
            if (printStackTrace) this.error.internalException.printStackTraceIfDebug()

            DomainResult.Error(this.error)
        }
        is DataResult.Success -> {
            successHandler(this.data)
        }
    }
}

/**
 * Chain extension function that allows to execute the code in case if the caller's [DomainResult]
 * instance is in [DomainResult.Success] state.
 *
 * @param executable the block of code that needs to be executed when the caller's [DomainResult]
 * is 'Success'.
 *
 * @return the same instance of the caller's [DomainResult] to allow the chaining with other
 * extension handling functions.
 */
fun <T> DomainResult<T>.onSuccess(executable: (T) -> Unit): DomainResult<T> = apply {
    if (this is DomainResult.Success) executable(data)
}

/**
 * Chain extension function that allows to execute the code in case if the caller's [DomainResult]
 * instance is in [DomainResult.Success] state. It differs from [onSuccess] in the sense that it
 * accepts the suspending executable blocks of code.
 *
 * @param executable the suspend block of code that needs to be executed when the caller's
 * [DomainResult] is 'Success'.
 *
 * @return the same instance of the caller's [DomainResult] to allow the chaining with other
 * extension handling functions.
 */
suspend fun <T> DomainResult<T>.onSuccessSuspend(executable: suspend (T) -> Unit): DomainResult<T> =
    apply {
        if (this is DomainResult.Success) executable(data)
    }

/**
 * Chain extension function that allows to execute the code in case if the caller's [DomainResult]
 * instance is in [DomainResult.Error] state.
 *
 * @param shouldPrintStackTrace param that indicates if the printing of the
 * [DomainResult.Error.error]'s stack trace is required. By default equals to 'true'.
 *
 * @param executable the block of code that needs to be executed when the caller's [DomainResult]
 * is 'Success'.
 *
 * @return the same instance of the caller's [DomainResult] to allow the chaining with other
 * extension handling functions.
 */
fun <T> DomainResult<T>.onError(
    shouldPrintStackTrace: Boolean = true,
    executable: (BaseError) -> Unit
): DomainResult<T> = apply {
    if (this is DomainResult.Error) {
        if (shouldPrintStackTrace) error.internalException.printStackTraceIfDebug()
        executable(error)
    }
}