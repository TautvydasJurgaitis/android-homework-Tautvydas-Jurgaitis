package com.vinted.demovinted.data.helpers

import com.vinted.demovinted.data.models.FilteringProperties
import com.vinted.demovinted.data.models.ItemBrand
import java.util.HashSet
import java.util.TreeMap

class DominantBrandResolver {

    fun resolveDominantBrand(
        items: List<ItemBrand>,
        filteringProperties: FilteringProperties.Default,
        itemsPerPage: Int
    ): ItemBrand? {

        // Resolving brand by filtering properties
        val brandsFromFilters = filteringProperties.brands
        if (brandsFromFilters.isNotEmpty()) {
            return if (brandsFromFilters.size == 1) {
                val brandId = brandsFromFilters.first().id
                // Brand from filters does not have all params, need to take from items
                items.firstOrNull { it.id == brandId }
            } else {
                null
            }
        }
        // Resolving brand by items
        val brands = HashSet<String>()
        val allBrands = arrayListOf<ItemBrand>()
        val allBrandIds = arrayListOf<String>()
        val brandsOccurrence = TreeMap<Int, ItemBrand?>(reverseOrder<Int>())

        for (item in items) {
            if (item.title.isNotEmpty()) {
                allBrands.add(item)
                allBrandIds.add(item.id)
                brands.add(item.id)
            }
        }
        if (allBrands.size > (itemsPerPage / 2)) {
            for (brand in brands) {
                brandsOccurrence[allBrandIds.count { it == brand }] = allBrands.firstOrNull { it.id == brand }
            }

            if (!brandsOccurrence.isEmpty()) {
                val key = brandsOccurrence.firstKey()
                return if (key > (allBrands.size * BRAND_APPEAR_FREQUENCY)) {
                    if (brandsOccurrence[key]?.title.isNullOrEmpty()) null else brandsOccurrence[key]
                } else {
                    null
                }
            }
        }
        return null
    }

    companion object {
        private const val BRAND_APPEAR_FREQUENCY = 0.8
    }
}
