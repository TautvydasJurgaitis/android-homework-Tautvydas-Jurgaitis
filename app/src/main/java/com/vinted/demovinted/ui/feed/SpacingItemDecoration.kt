package com.vinted.demovinted.ui.feed

import android.graphics.Rect
import android.view.View
import androidx.annotation.IntDef
import androidx.recyclerview.widget.RecyclerView

open class SpacingItemDecoration(
        private val offset: Int,
        @Orientation
        private val orientation: Int
) : RecyclerView.ItemDecoration() {

    @IntDef(VERTICAL_SPACING, HORIZONTAL_SPACING, VERTICAL_HORIZONTAL_SPACING)
    annotation class Orientation

    override fun getItemOffsets(
            outRect: Rect, view: View, parent: RecyclerView,
            state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        when (orientation) {
            HORIZONTAL_SPACING -> outRect.set(offset, 0, offset, 0)
            VERTICAL_SPACING -> outRect.set(0, offset, 0, offset)
            VERTICAL_HORIZONTAL_SPACING -> outRect.set(offset, offset, offset, offset)
        }
    }

    companion object {
        const val VERTICAL_SPACING = 1
        const val HORIZONTAL_SPACING = 2
        const val VERTICAL_HORIZONTAL_SPACING = 3
    }
}