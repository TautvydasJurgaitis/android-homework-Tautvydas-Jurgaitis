package com.vinted.demovinted.domain.repository

import com.vinted.demovinted.data.models.ItemSeenEvent
import com.vinted.demovinted.data.network.responses.CatalogItemListResponse
import retrofit2.Response

interface FeedRepository {
    suspend fun addImpressions(seenEvents: List<ItemSeenEvent>): Response<Void>
    suspend fun getSearchedItemsFeed(page: Int, searchQuery: String): CatalogItemListResponse

}