package com.vinted.demovinted.ui.feed

import android.view.ViewGroup
import android.widget.AbsListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class UniversalScrollListener : RecyclerView.OnScrollListener(), AbsListView.OnScrollListener {

    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0
    var firstVisibleItem: Int = 0

    override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {}

    override fun onScroll(view: AbsListView, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
        onScrolled(view, firstVisibleItem, visibleItemCount, totalItemCount)
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager

        visibleItemCount = recyclerView.childCount
        totalItemCount = layoutManager.itemCount
        firstVisibleItem = layoutManager.findFirstVisibleItemPosition()
        onScrolled(recyclerView, firstVisibleItem, visibleItemCount, totalItemCount)
    }

    abstract fun onScrolled(viewGroup: ViewGroup, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int)
}