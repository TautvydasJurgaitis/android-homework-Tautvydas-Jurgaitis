package com.vinted.demovinted.data.interactors

import com.vinted.demovinted.data.models.Content
import com.vinted.demovinted.data.models.ItemBoxViewEntity
import com.vinted.demovinted.data.models.ItemBrand
import com.vinted.demovinted.data.models.SearchData
import com.vinted.demovinted.data.network.responses.FetchItemsResult
import com.vinted.demovinted.data.network.responses.PaginationState
import io.reactivex.Flowable

abstract class AbstractItemProvider(
    private val itemsPerPage: Int
) : ItemProvider {

    override var pagination: PaginationState = PaginationState()
    protected val page: Int
        get() = pagination.currentPage
    protected val totalEntries: Int
        get() = pagination.totalEntries
    protected val hasMoreItems: Boolean
        get() = pagination.hasMoreItems()
    protected val loadedItemsIds = mutableSetOf<String>()

    override var searchData: SearchData? = null

    protected abstract val flowTerminator: Content

    override fun getItemFlow(): Flowable<FetchItemsResult> {
        pagination = PaginationState()
        loadedItemsIds.clear()
        return Flowable.range(1, Int.MAX_VALUE)
            .flatMap({
                retrieveCatalogItems()
            },
                false,
                1,
                1)
            .takeWhile {
                it != flowTerminator
            }
            .buffer(itemsPerPage)
            .map { items ->
                FetchItemsResult(
                    items = mapToItemBoxViewEntity(items),
                    totalEntries = totalEntries,
                    hasMoreItems = hasMoreItems,
                    dominantBrand = resolveDominantBrand(items),
                    searchData = searchData)
            }
            .defaultIfEmpty(
                FetchItemsResult(
                    items = emptyList(),
                    totalEntries = totalEntries,
                    hasMoreItems = false,
                    dominantBrand = null,
                    searchData = searchData
                ))
    }

    protected abstract fun retrieveCatalogItems(): Flowable<Content>
    protected abstract fun mapToItemBoxViewEntity(items: List<Content>): List<ItemBoxViewEntity>
    protected abstract fun resolveDominantBrand(items: List<Content>): ItemBrand?

    companion object {
        const val PAGE = "page"
        const val PER_PAGE = "per_page"
        const val TIME = "time"
        const val SESSION_ID = "search_session_id"
    }
}