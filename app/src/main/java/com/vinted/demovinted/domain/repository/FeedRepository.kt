package com.vinted.demovinted.domain.repository

import com.vinted.demovinted.data.models.ItemSeenEvent
import retrofit2.Response

interface FeedRepository {
    suspend fun addImpressions(seenEvents: List<ItemSeenEvent>): Response<Void>

}