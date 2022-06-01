package com.elevenine.artinstitute.ui.model

import androidx.annotation.StringRes
import com.elevenine.artinstitute.R

/**
 * @author Sherzod Nosirov
 * @since 17.03.2022
 */

/**
 * Data class that represents a UI message that can be passed by a ViewModel to its View.
 */
class UiMessage(
    val id: Long,
    val message: String? = null,
    @StringRes val messageResId: Int = R.string.error_unknown
)