package com.vinted.demovinted.data.interactors

import com.vinted.demovinted.data.models.SearchData
import com.vinted.demovinted.data.network.responses.FetchItemsResult
import com.vinted.demovinted.data.network.responses.PaginationState
import io.reactivex.Flowable

interface ItemProvider {
    fun getItemFlow(): Flowable<FetchItemsResult>
    var pagination: PaginationState
    var searchData: SearchData?
}