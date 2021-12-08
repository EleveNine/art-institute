package com.elevenine.artinstitute.utils

import android.app.Activity
import android.content.res.Resources
import android.os.Build
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import com.elevenine.artinstitute.R

/**
 * @author Sherzod Nosirov
 * @since 08.12.2021
 */

/**
 * This method sets the icons of the status bar for the given fragment either light or dark.
 *
 * If called from Fragment, this method must be called from Fragment's onResume() method.
 *
 * @param isLight if true, then the statusBar bg color is light, thus the icons of the statusBar
 * must be dark and vice versa.
 */
fun Fragment.setLightStatusBar(isLight: Boolean) {
    WindowInsetsControllerCompat(
        requireActivity().window,
        requireActivity().window.decorView
    ).isAppearanceLightStatusBars = isLight
}

/**
 * This method sets the icons of the status bar for the given activity either light or dark.
 *
 * If called from Fragment, this method must be called from Fragment's onResume() method.
 *
 * @param isLight if true, then the statusBar bg color is light, thus the icons of the statusBar
 * must be dark and vice versa.
 */
fun Activity.setLightStatusBar(isLight: Boolean) {
    WindowInsetsControllerCompat(
        window,
        window.decorView
    ).isAppearanceLightStatusBars = isLight
}

/**
 * This method sets the background color of the navigation bar and the icons to be either light
 * or dark depending on the current navigation bar's background color.
 *
 * This method is applied only for Android 8.1+. For lower versions the color of the navigation
 * bar remains as in the default theme.
 *
 * IMPORTANT: If called from Fragment, this method must be called from Fragment's onResume()
 * method.
 *
 * @param colorResId the id of the color that is the new navBar's background color.
 *
 * @param isLight if true, then the navBar bg color is light, thus the icons of the navBar
 * must be dark and vice versa.
 */
fun Fragment.setNavigationBarColor(colorResId: Int, isLight: Boolean = true) {
    val activity = requireActivity()
    val fallbackColorId = R.color.color_window_default_nav_bar

    if (Build.VERSION.SDK_INT > 26) {
        try {
            activity.window.navigationBarColor =
                ContextCompat.getColor(activity, colorResId)

            WindowInsetsControllerCompat(
                activity.window,
                activity.window.decorView
            ).isAppearanceLightNavigationBars = isLight

        } catch (e: Resources.NotFoundException) {
            e.printStackTraceIfDebug()

            activity.window.navigationBarColor =
                ContextCompat.getColor(activity, fallbackColorId)

            WindowInsetsControllerCompat(
                activity.window,
                activity.window.decorView
            ).isAppearanceLightNavigationBars = false
        }
    }
}

/**
 * This method sets the background color of the navigation bar and the icons to be either light
 * or dark depending on the current navigation bar's background color.
 *
 * This method is applied only for Android 8.1+. For lower versions the color of the navigation
 * bar remains as in the default theme.
 *
 * IMPORTANT: If called from Fragment, this method must be called from Fragment's onResume()
 * method.
 *
 * @param colorResId the id of the color that is the new navBar's background color.
 *
 * @param isLight if true, then the navBar bg color is light, thus the icons of the navBar
 * must be dark and vice versa.
 */
fun Activity.setNavigationBarColor(colorResId: Int, isLight: Boolean = true) {
    val fallbackColorId = R.color.color_window_default_nav_bar

    if (Build.VERSION.SDK_INT > 26) {
        try {
            window.navigationBarColor =
                ContextCompat.getColor(this, colorResId)

            WindowInsetsControllerCompat(
                window,
                window.decorView
            ).isAppearanceLightNavigationBars = isLight

        } catch (e: Resources.NotFoundException) {
            e.printStackTraceIfDebug()

            window.navigationBarColor =
                ContextCompat.getColor(this, fallbackColorId)

            WindowInsetsControllerCompat(
                window,
                window.decorView
            ).isAppearanceLightNavigationBars = false
        }
    }
}

/**
 * Utility method that automatically sets insets listener and applies default insets (status
 * bar and navigation bar in particular) to the padding of the provided [targetView].
 *
 * ATTENTION: call this method only in Fragment's onViewCreated method.
 */
fun Fragment.applyDefaultInsets(targetView: View) {
    val initTopPadding = targetView.paddingTop

    ViewCompat.setOnApplyWindowInsetsListener(targetView) { _, insets ->

        val statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
        val navBarHeight = insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom

        targetView.updatePadding(0, statusBarHeight + initTopPadding, 0, navBarHeight)

        insets
    }
}