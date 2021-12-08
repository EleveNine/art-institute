package com.elevenine.artinstitute.utils

import com.elevenine.artinstitute.BuildConfig

/**
 * @author Sherzod Nosirov
 * @since 08.12.2021
 */

/**
 * Utility method that prints the [Throwable]'s stackTrace only when the app is in the DEBUG mode.
 */
fun Throwable.printStackTraceIfDebug() {
    if (BuildConfig.DEBUG) this.printStackTrace()
}