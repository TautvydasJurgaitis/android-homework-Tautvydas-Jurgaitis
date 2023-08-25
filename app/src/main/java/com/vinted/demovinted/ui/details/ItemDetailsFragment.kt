package com.vinted.demovinted.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderViewAdapter
import com.vinted.demovinted.R
import com.vinted.demovinted.data.models.ItemBoxViewEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.carousel_item.view.*
import kotlinx.android.synthetic.main.fragment_item_details.*

@AndroidEntryPoint
class ItemDetailsFragment : Fragment() {

    private val item: ItemBoxViewEntity by lazy {
        requireArguments().getParcelable<ItemBoxViewEntity>(EXTRA_ITEM)!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_item_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        image_carousel.setSliderAdapter(SimpleSliderAdapter(listOf(item.mainPhoto!!.url)), false)
        image_carousel.setIndicatorAnimation(IndicatorAnimationType.SLIDE)
        setItem()
    }

    private fun setItem() {
        item_category.text = item.category
        item_price.text = "${item.price?.setScale(2)} €"
        item_brand.text = item.brandTitle
        item.size?.let { item_size.text = it } ?: run {
            item_size.visibility = View.GONE
            item_size_title.visibility = View.GONE
        }
    }

    inner class SimpleSliderAdapter(private val photos: List<String>): SliderViewAdapter<SimpleSliderAdapter.SimpleViewHolder>() {

        inner class SimpleViewHolder(view: View): SliderViewAdapter.ViewHolder(view)

        override fun onCreateViewHolder(parent: ViewGroup?): SimpleViewHolder {
            val view = LayoutInflater.from(parent!!.context).inflate(R.layout.carousel_item, parent, false)
            return SimpleViewHolder(view)
        }

        override fun getCount(): Int = photos.size

        override fun onBindViewHolder(viewHolder: SimpleViewHolder?, position: Int) {
            val imageView = viewHolder?.itemView?.image!!
            Glide.with(imageView).load(photos[position]).into(imageView)
        }
    }

    companion object {

        fun newInstance(item: ItemBoxViewEntity): ItemDetailsFragment {
            return ItemDetailsFragment().apply {
                arguments = Bundle().apply { putParcelable(EXTRA_ITEM, item) }
            }
        }

        private const val EXTRA_ITEM = "EXTRA_ITEM"
    }
}
