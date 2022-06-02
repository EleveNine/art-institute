package com.elevenine.artinstitute.utils

import android.content.res.Resources

/**
 * Convert the value from dp to px.
 */
val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

/**
 * Convert the value from sp to px.
 */
val Int.sp: Int
    get() = (this * Resources.getSystem().displayMetrics.scaledDensity).toInt()