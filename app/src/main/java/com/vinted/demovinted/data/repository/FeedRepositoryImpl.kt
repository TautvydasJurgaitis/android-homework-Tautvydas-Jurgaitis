package com.vinted.demovinted.data.repository

import com.vinted.demovinted.data.models.ItemSeenEvent
import com.vinted.demovinted.data.network.api.Api
import com.vinted.demovinted.domain.repository.FeedRepository
import retrofit2.Response
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(
    private val api: Api
) : FeedRepository {

    override suspend fun addImpressions(seenEvents: List<ItemSeenEvent>): Response<Void> {
        return api.postImpressions(seenEvents)
    }
}