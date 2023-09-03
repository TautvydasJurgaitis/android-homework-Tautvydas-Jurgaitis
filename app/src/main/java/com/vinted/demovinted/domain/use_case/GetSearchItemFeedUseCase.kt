package com.vinted.demovinted.domain.use_case

import com.vinted.demovinted.data.models.ItemSeenEvent
import com.vinted.demovinted.data.network.responses.CatalogItemListResponse
import com.vinted.demovinted.domain.data.DataState
import com.vinted.demovinted.domain.repository.FeedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class GetSearchItemFeedUseCase  @Inject constructor(
    private val repository: FeedRepository
) {
    fun execute(page: Int, searchQuery: String): Flow<DataState<CatalogItemListResponse>> = flow {
        try {
            emit(DataState.loading())
            val feed = repository.getSearchedItemsFeed(page, searchQuery)
            emit(DataState.success(feed))
        } catch (e: HttpException) {
            emit(DataState.error<CatalogItemListResponse>("An unexpected error occurred"))
        }
    }
}