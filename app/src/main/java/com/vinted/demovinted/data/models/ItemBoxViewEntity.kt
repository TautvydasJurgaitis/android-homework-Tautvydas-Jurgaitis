package com.vinted.demovinted.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal

@Parcelize
data class ItemBoxViewEntity(
    val itemId: String = "",
    val owner: Boolean = false,
    val mainPhoto: Photo? = null,
    val price: BigDecimal,
    val discountPrice: BigDecimal? = null,
    val category: String,
    val isForSwap: Boolean = false,
    val badge: ItemBadge? = null,
    val favouritesCount: Int = 0,
    val viewCount: Int = 0,
    val brandTitle: String? = null,
    val size: String? = null,
    val mediaSize: Int = 0,
    val canPushUpNow: Boolean = false,
    val statsVisible: Boolean = false,
    val pushUpPossible: Boolean = false,
    val promoted: Boolean = false,
    val itemCatalogId: String? = null,
    val itemColor1Id: String? = null,
    val itemStatusId: String? = null,
    val createdAt: String? = null,
    val searchScore: String? = null,
    val totalFee: String? = null,
    val isDraft: Boolean = false,
    val isReplicaProofOrUnderReview: Boolean = false
) : Parcelable {

    companion object {

        fun fromCatalogItem(catalogItem: CatalogItem): ItemBoxViewEntity {
            return ItemBoxViewEntity(
                owner = false,
                itemId = catalogItem.id.toString(),
                mainPhoto = catalogItem.mainPhoto,
                price = catalogItem.price,
                brandTitle = catalogItem.itemBrand.title,
                searchScore = catalogItem.searchScore,
                category = catalogItem.category
            )
        }
    }
}
