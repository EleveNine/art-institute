package com.elevenine.artinstitute.data.common

/**
 * @author Sherzod Nosirov
 * @since July 31, 2021
 */

sealed class DataResult<out R> {

    data class Success<out T>(val data: T) : DataResult<T>()

    data class Error(val error: BaseError) : DataResult<Nothing>()
}

