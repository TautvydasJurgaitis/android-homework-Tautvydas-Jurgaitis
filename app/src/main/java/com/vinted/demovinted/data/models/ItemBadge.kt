package com.vinted.demovinted.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemBadge(
    val theme: String? = null,
    val body: String? = null,
    val icon: String? = null
) : Parcelable
