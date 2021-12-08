package com.elevenine.artinstitute.utils

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions

/**
 * @author Sherzod Nosirov
 * @since 07.10.2021
 */

/**
 * Allows to safely navigate to the given direction and helps to eliminate situations when multiple
 * fast user taps may cause several overlapping successive calls to the NavController and cause a
 * crash.
 */
fun NavController.navigateSafely(direction: NavDirections) {
    currentDestination?.getAction(direction.actionId)?.let { navigate(direction) }
}

fun NavController.navigateSafely(direction: NavDirections, navOptions: NavOptions) {
    currentDestination?.getAction(direction.actionId)?.let { navigate(direction, navOptions) }
}

fun NavController.navigateSafely(@IdRes directionId: Int, bundle: Bundle, navOptions: NavOptions) {
    currentDestination?.getAction(directionId)?.let { navigate(directionId, bundle, navOptions) }
}