package com.elevenine.artinstitute.data.common

/**
 * @author Sherzod Nosirov
 * @since July 31, 2021
 */

sealed class DataResult<out R> {

    data class OnSuccess<out T>(val data: T) : DataResult<T>()

    data class OnError(val error: BaseError) : DataResult<Nothing>()
}

