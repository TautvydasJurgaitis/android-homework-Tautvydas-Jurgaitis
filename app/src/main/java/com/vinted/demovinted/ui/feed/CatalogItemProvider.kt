package com.vinted.demovinted.data.interactors

import com.vinted.demovinted.data.helpers.DominantBrandResolver
import com.vinted.demovinted.data.helpers.filterValuesNotNull
import com.vinted.demovinted.data.models.CatalogItem
import com.vinted.demovinted.data.models.Content
import com.vinted.demovinted.data.models.FilteringProperties
import com.vinted.demovinted.data.models.ItemBoxViewEntity
import com.vinted.demovinted.data.models.ItemBrand
import com.vinted.demovinted.data.models.SearchData
import com.vinted.demovinted.data.network.api.Api
import io.reactivex.Flowable
import io.reactivex.Single

class CatalogItemProvider(
    private val api: Api,
    private val filteringProperties: FilteringProperties.Default,
    private val itemsPerPage: Int,
    private val dominantBrandResolver: DominantBrandResolver
) : AbstractItemProvider(itemsPerPage) {

    override val flowTerminator =
        CatalogItem(title = "Terminator item")

    override fun retrieveCatalogItems(): Flowable<Content> {
        return buildParams()
            .flatMap { params ->
                api.getItemsFeed(params)
            }
            .doOnSuccess {
                pagination = pagination.copy(currentPage = pagination.currentPage + 1)
            }
            .doOnSuccess {
                searchData = SearchData(
                    correlationId = it.searchCorrelationId,
                    sessionId = it.searchSessionId
                )
            }
            .map {
                val scores = it.itemScoresById.orEmpty()
                it.items.onEach { item -> item.searchScore = scores[item.id.toString()] }
            }
            .map { it.removeDuplicates() }
            .toFlowable()
            .flatMap({ Flowable.fromIterable(it).defaultIfEmpty(flowTerminator) }, false, 1, 1)
    }

    override fun mapToItemBoxViewEntity(items: List<Content>): List<ItemBoxViewEntity> {
        return items.map {
            ItemBoxViewEntity.fromCatalogItem(it as CatalogItem)
        }
    }

    override fun resolveDominantBrand(items: List<Content>): ItemBrand? {
        return dominantBrandResolver.resolveDominantBrand(
            items = items.map { (it as CatalogItem).itemBrand },
            filteringProperties = filteringProperties,
            itemsPerPage = itemsPerPage
        )
    }

    private fun buildParams(): Single<Map<String, String>> {
        return prepareParams(filteringProperties)
    }

    private fun prepareParams(filteringProperties: FilteringProperties.Default): Single<Map<String, String>> {
        return Single.just(
            filteringProperties.toMap() + mapOf(
                PAGE to page.toString(),
                PER_PAGE to itemsPerPage.toString(),
                TIME to pagination.time.toString(),
                SESSION_ID to searchData?.sessionId
            ).filterValuesNotNull()
        )
    }

    private fun List<CatalogItem>.removeDuplicates(): List<CatalogItem> {
        val uniqueItems = this.distinctBy { it.id }.toMutableList()
        val uniqueNewItems = uniqueItems.filterNot { loadedItemsIds.contains(it.id.toString()) }
        uniqueNewItems.forEach { loadedItemsIds.add(it.id.toString()) }
        return uniqueNewItems.toList()
    }
}
