package com.elevenine.artinstitute.ui.list.item_decorations

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Used to add space dividers to the vertical RecyclerViews.
 *
 * @param divider the overall space's height between 2 views in the RecyclerView.
 */
class SpaceVerticalDividerDecoration(
    private val divider: Int,
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val oneSideVerticalDivider = divider / 2

        with(outRect) {
            top = oneSideVerticalDivider
            bottom = oneSideVerticalDivider
        }
    }

}