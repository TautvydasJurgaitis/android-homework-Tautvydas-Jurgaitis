package com.vinted.demovinted.domain.use_case

import com.vinted.demovinted.data.models.ItemSeenEvent
import com.vinted.demovinted.domain.data.DataState
import com.vinted.demovinted.domain.repository.FeedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class PostItemSeenUseCase  @Inject constructor(
    private val repository: FeedRepository
) {
    fun execute(seenEvents: List<ItemSeenEvent>): Flow<DataState<Response<Void>>> = flow {
        try {
            emit(DataState.loading())
            val postItem = repository.addImpressions(seenEvents)
            emit(DataState.success(postItem))
        } catch (e: HttpException) {
            emit(DataState.error<Response<Void>>("An unexpected error occurred"))
        }
    }
}