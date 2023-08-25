package com.vinted.demovinted.ui.feed

import android.view.ViewGroup

class EndlessScrollListener(val loaditemperpage: Int = 20, val load: () -> Unit) : UniversalScrollListener() {

    var isLoading: Boolean = false
    var isEnabled: Boolean = true

    override fun onScrolled(viewGroup: ViewGroup, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
        if (!isLoading && isEnabled && (totalItemCount - firstVisibleItem - visibleItemCount) < loaditemperpage / 2) {
            load()
        }
    }
}