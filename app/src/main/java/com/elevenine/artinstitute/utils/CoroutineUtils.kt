package com.elevenine.artinstitute.utils

import com.elevenine.artinstitute.common.DataResult
import kotlinx.coroutines.CancellationException

/**
 * Utility function to properly handle exceptions in coroutines. Since standard
 * try-catch(e: Exception) mechanism catches all the exceptions, it is possible to catch the
 * [CancellationException] which is required in order to cancel the coroutine, when for example
 * Job.cancel() is called. Thus, in order to not to miss the Cancellation, this function should be
 * used for every try-catch operation inside a coroutine.
 * This function should primarily be used in Repository implementations.
 *
 * @param tryBlock the unsafe block of code that must be performed in the 'try' block. Returns the
 * [DataResult] instance.
 *
 * @param catchBlock the desired action to be performed when exception is caught and it is not a
 * [CancellationException]. Returns the [DataResult.Error].
 */
fun <T> tryCatchSafely(
    tryBlock: () -> DataResult<T>,
    catchBlock: (Exception) -> DataResult.Error
): DataResult<T> {
    return try {
        tryBlock()
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        catchBlock(e)
    }
}

/**
 * Same as [tryCatchSafely], but for the suspendable blocks of code.
 */
suspend fun <T> tryCatchSafelySuspend(
    tryBlock: suspend () -> DataResult<T>,
    catchBlock: (Exception) -> DataResult.Error
): DataResult<T> {
    return try {
        tryBlock()
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        catchBlock(e)
    }
}