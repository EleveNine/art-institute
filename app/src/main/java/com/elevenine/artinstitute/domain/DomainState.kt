package com.elevenine.artinstitute.domain

import com.elevenine.artinstitute.data.common.BaseError

/**
 * @author Sherzod Nosirov
 * @since 12.01.2022
 */

sealed class DomainState<out R> {

    data class Success<out T>(val data: T) : DomainState<T>()

    data class Error(val error: BaseError) : DomainState<Nothing>()

    data class Loading(val message: String? = null) : DomainState<Nothing>()
}