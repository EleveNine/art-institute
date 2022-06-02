package com.elevenine.artinstitute.ui.list.item_decorations

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.elevenine.artinstitute.R
import com.elevenine.artinstitute.utils.dp

/**
 * Special gray line that divides the recycler view's items vertically.
 */
class LineVerticalDividerDecoration(context: Context) :
    RecyclerView.ItemDecoration() {

    private val divider = ContextCompat.getDrawable(
        context,
        R.drawable.bg_divider
    )

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        if (parent.getChildAdapterPosition(view) + 1
            != parent.adapter?.itemCount
        ) {
            outRect.bottom = 0
        }
    }

    override fun onDraw(
        c: Canvas, parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.onDraw(c, parent, state)

        val left: Int = parent.paddingLeft
        val right: Int = parent.width - parent.paddingRight

        for (i in 0 until parent.childCount - 1) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom: Int = top + 1.dp
            divider?.setBounds(left, top, right, bottom)
            divider?.draw(c)
        }
    }
}