package com.vinted.demovinted.data.network.responses

import com.squareup.moshi.Json
import com.vinted.demovinted.data.models.CatalogItem

open class CatalogItemListResponse(
    val items: List<CatalogItem> = emptyList(),
    val searchCorrelationId: String? = "",
    val itemScoresById: Map<String, String>? = null,
    val searchSessionId: String? = ""
)

data class PaginationState(
    @Json(name = "current_page") val currentPage: Int = 0,
    @Json(name = "total_entries")val totalEntries: Int = DEFAULT_PAGE_SIZE,
    @Json(name = "per_page")val perPage: Int = DEFAULT_PAGE_SIZE,
    val time: Long = System.currentTimeMillis()
) {

    fun hasMoreItems(): Boolean {
        return true
    }

    companion object {
        const val DEFAULT_PAGE_SIZE = 20
    }
}
