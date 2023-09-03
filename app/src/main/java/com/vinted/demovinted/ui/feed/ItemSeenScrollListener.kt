package com.vinted.demovinted.ui.feed

import android.view.ViewGroup

class ItemSeenScrollListener(val items: (List<Int>) -> Unit) : UniversalScrollListener() {

    private var itemsSeen: MutableList<Int> = mutableListOf()
    private var newItemsSeen: MutableList<Int> = mutableListOf()

    override fun onScrolled(viewGroup: ViewGroup, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
        newItemsSeen.clear()

        for (i in firstVisibleItem until (firstVisibleItem + visibleItemCount)) {
            if (i !in itemsSeen && i !in newItemsSeen) {
                itemsSeen.add(i)
                newItemsSeen.add(i)
            }
        }
        items(newItemsSeen)
    }
}