package com.vinted.demovinted.ui.feed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.vinted.demovinted.R
import com.vinted.demovinted.data.interactors.CatalogLoaderInteractor.Companion.ITEMS_PER_PAGE
import com.vinted.demovinted.data.network.responses.FetchItemsResult
import com.vinted.demovinted.data.models.ItemBoxViewEntity
import com.vinted.demovinted.ui.details.ItemDetailsFragment
import com.vinted.demovinted.ui.utils.ListStatus
import com.vinted.demovinted.ui.utils.Success
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.feed_fragment.*

@AndroidEntryPoint
class FeedFragment : Fragment() {

    private val viewModel: FeedViewModel by viewModels()
    private val items = mutableListOf<ItemBoxViewEntity>()

    private val itemClickListener: (ItemBoxViewEntity) -> Unit = {
        val fragment = ItemDetailsFragment.newInstance(it)

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }

    private val feedAdapter = FeedAdapter(items, itemClickListener)

    private val scrollListener: EndlessScrollListener = EndlessScrollListener(ITEMS_PER_PAGE) {
        viewModel.requestMore()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        return inflater.inflate(R.layout.feed_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.feedStatus.observe(viewLifecycleOwner, Observer { status ->
            handleNewStatus(status)
        })
        feed_list.apply {
            adapter = feedAdapter
            (layoutManager as GridLayoutManager).spanCount = 2
            addOnScrollListener(scrollListener)
            addItemDecoration(EvenSpacingItemDecorator((resources.displayMetrics.density * 8).toInt()))
        }
    }

    private fun handleNewStatus(status: ListStatus?) {
        status?.let {
            when (it) {
                is Success<*> -> handleSuccess(it)
                is com.vinted.demovinted.ui.utils.Error -> showError(it.t.message)
            }
        }
    }

    private fun handleSuccess(result: Success<*>) {
        val extractedResult = result.data as FetchItemsResult
        scrollListener.isEnabled = extractedResult.hasMoreItems
        val items = extractedResult.items

        val oldCount = this.items.count()
        val newItems = items.removeDuplicates(this.items)
        this.items.addAll(items as List<ItemBoxViewEntity>)
        feedAdapter.notifyItemRangeInserted(oldCount, newItems.count())
    }

    private fun List<Any>.removeDuplicates(currentItems: List<Any>): List<Any> {
        val result = this - currentItems
        val itemIds = currentItems.filterIsInstance<ItemBoxViewEntity>().map { it.itemId }
        return result.filterIsInstance<ItemBoxViewEntity>()
            .filterNot { itemIds.contains(it.itemId) }
    }

    private fun showError(message: String?) {
        Toast.makeText(requireActivity(), "Something went wrong - $message", Toast.LENGTH_LONG)
            .show()
    }

    companion object {
        fun newInstance() = FeedFragment()
    }
}
