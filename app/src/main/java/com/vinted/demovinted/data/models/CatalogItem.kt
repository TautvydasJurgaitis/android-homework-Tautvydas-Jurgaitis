package com.vinted.demovinted.data.models

import com.squareup.moshi.Json
import java.math.BigDecimal

class CatalogItem(
    val id: Int = 0,
    val title: String = "",
    val price: BigDecimal = BigDecimal.ONE,
    private val photo: String = "",
    private val brand: String = "",
    val category: String = ""
) : Content {

    var searchScore: String? = null

    val mainPhoto: Photo
        get() = Photo(url = "http://mobile-homework-api.vinted.net/images/$photo")

    val itemBrand: ItemBrand
        get() = ItemBrand.createNoBrand(brand)
}