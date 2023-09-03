package com.vinted.demovinted.data.network.api

import com.vinted.demovinted.data.models.ItemSeenEvent
import com.vinted.demovinted.data.network.responses.CatalogItemListResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface Api {

    @GET("items")
    fun getItemsFeed(
        @QueryMap params: Map<String, String>
    ): Single<CatalogItemListResponse>

    @GET("items")
    suspend fun getSearchedItemsFeed(
        @Query("page") page: Int,
        @Query("search_text") query: String
    ): CatalogItemListResponse

    @POST("impressions")
    suspend fun postImpressions(@Body itemsSeen: List<ItemSeenEvent>): Response<Void>
}
