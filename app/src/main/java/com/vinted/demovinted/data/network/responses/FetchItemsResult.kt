package com.vinted.demovinted.data.network.responses

import com.vinted.demovinted.data.models.ItemBrand
import com.vinted.demovinted.data.models.SearchData

data class FetchItemsResult(
    val items: List<Any>,
    val totalEntries: Int,
    val hasMoreItems: Boolean,
    val dominantBrand: ItemBrand? = null,
    val searchData: SearchData? = null
)