package com.vinted.demovinted.ui.feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vinted.demovinted.R
import com.vinted.demovinted.data.models.ItemBoxViewEntity
import kotlinx.android.synthetic.main.item_feed.view.*

class FeedAdapter(
    private val items: List<ItemBoxViewEntity>,
    private val onItemClick: (ItemBoxViewEntity) -> Unit
) : RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_feed, parent, false)
        return FeedViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener { onItemClick(items[position]) }
    }

    inner class FeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: ItemBoxViewEntity?) {
            item?.let {
                itemView.item_category.text = item.category
                itemView.item_price.text = "${item.price?.setScale(2)} €"
                itemView.item_brand.text = item.brandTitle
                item.size?.let { itemView.item_size.text = it } ?: run {
                    itemView.item_size.visibility = View.GONE
                }

                Glide.with(itemView)
                    .load(item.mainPhoto?.url)
                    .fallback(R.color.colorGray)
                    .into(itemView.item_image)
            }
        }
    }
}