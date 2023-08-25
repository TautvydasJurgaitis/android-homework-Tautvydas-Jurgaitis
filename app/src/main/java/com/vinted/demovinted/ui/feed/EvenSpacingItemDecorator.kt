package com.vinted.demovinted.ui.feed

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vinted.demovinted.R

class EvenSpacingItemDecorator(
    private val offset: Int
) : SpacingItemDecoration(offset, VERTICAL_HORIZONTAL_SPACING) {

    override fun getItemOffsets(outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val rowSpanCount = 2

        val spanIndex = (view.layoutParams as GridLayoutManager.LayoutParams).spanIndex
        val spanSize = (view.layoutParams as GridLayoutManager.LayoutParams).spanSize

        val half = offset / 2

        outRect.top = half
        outRect.bottom = half
        outRect.left = if (spanIndex == 0) offset else half
        outRect.right =
            if (spanIndex == rowSpanCount - 1 || (spanIndex == rowSpanCount - 2 && spanSize == 2)) {
                offset
            } else {
                half
            }
    }
}