package com.vinted.demovinted.data.network.api

import com.vinted.demovinted.data.network.responses.CatalogItemListResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface Api {

    @GET("items")
    fun getItemsFeed(
        @QueryMap params: Map<String, String>
    ): Single<CatalogItemListResponse>
}
