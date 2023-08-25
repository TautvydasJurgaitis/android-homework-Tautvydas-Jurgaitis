package com.vinted.demovinted.data.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Photo(
    val id: String = "",
    val url: String = "",
    @Json(name = "is_main") val isMain: Boolean = false,
    @Json(name = "full_size_url") val fullSizeUrl: String? = null
) : Parcelable