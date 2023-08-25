package com.vinted.demovinted.data.models

import java.math.BigDecimal

sealed class FilteringProperties {

    data class Default(
        val sizes: Set<ItemSize> = emptySet(),
        val categoryId: String? = null,
        val priceFrom: BigDecimal? = null,
        val priceTo: BigDecimal? = null,
        val query: String? = null,
        val isForSwap: Boolean = false,
        val colors: List<ItemColor> = emptyList(),
        val materials: List<ItemMaterial> = emptyList(),
        val statuses: List<ItemStatus> = emptyList(),
        val brands: List<ItemBrand> = emptyList(),
        val countries: List<Country> = emptyList(),
        val cities: List<City> = emptyList(),
        val sortingOrder: SortingOrder = SortingOrder.CATALOG_SORTING_ORDERS.first(),
        val searchId: String? = null,
        val popularItems: Boolean = false,
        var isSubscribed: Boolean = false,
        val styles: List<ItemStyle> = emptyList()
    ) : FilteringProperties() {

        override fun toMap(): Map<String, String> {
            val result = mutableMapOf<String, String>()

            result += PARAM_ORDER to sortingOrder.key

            if (categoryId != null && styles.isEmpty()) {
                result += PARAM_CATALOGS to categoryId.toString()
            }

            if (styles.isNotEmpty()){
                result += PARAM_STYLES to styles.joinToString(",") { it.id }
            }

            if (colors.isNotEmpty()) {
                result += PARAM_COLORS to colors.joinToString(",") { it.id }
            }

            if (materials.isNotEmpty()) {
                result += PARAM_MATERIALS to materials.joinToString(",") { it.id }
            }

            if (statuses.isNotEmpty()) {
                result += PARAM_STATUSES to statuses.joinToString(",") { it.id }
            }

            if (brands.isNotEmpty()) {
                result += PARAM_BRANDS to brands.joinToString(",") { it.id.toString() }
            }

            if (sizes.isNotEmpty()) {
                result += PARAM_SIZES to sizes.joinToString(",") { it.id }
            }

            if (priceFrom != null) {
                result += PARAM_PRICE_FROM to priceFrom.toString()
            }

            if (priceTo != null) {
                result += PARAM_PRICE_TO to priceTo.toString()
            }

            if (countries.isNotEmpty()) {
                result += PARAM_COUNTRIES to countries.joinToString(",") { it.id }
            }

            if (cities.isNotEmpty()) {
                result += PARAM_CITIES to cities.joinToString(",") { it.id }
            }

            if (isForSwap) {
                result += PARAM_FOR_SWAP to "1"
            }

            if (searchId != null) {
                result += PARAM_SAVED_SEARCH to searchId
            }

            if (popularItems) {
                result += PARAM_POPULAR to "1"
            }

            return result
        }
    }

    data class SavedSearch(val searchId: String = "") : FilteringProperties() {
        override fun toMap(): Map<String, String> {
            return mapOf(PARAM_SAVED_SEARCH to searchId)
        }
    }

    abstract fun toMap(): Map<String, String>

    companion object {
        private const val PARAM_ORDER = "order"
        private const val PARAM_CATALOGS = "catalog_id"
        private const val PARAM_COLORS = "color_ids"
        private const val PARAM_MATERIALS = "material_ids"
        private const val PARAM_STATUSES = "status_ids"
        private const val PARAM_BRANDS = "brand_ids"
        private const val PARAM_SIZES = "size_ids"
        private const val PARAM_PRICE_FROM = "price_from"
        private const val PARAM_PRICE_TO = "price_to"
        private const val PARAM_COUNTRIES = "country_ids"
        private const val PARAM_CITIES = "city_ids"
        private const val PARAM_FOR_SWAP = "is_for_swap"
        private const val PARAM_SAVED_SEARCH = "saved_search_id"
        private const val PARAM_POPULAR = "popular"
        private const val PARAM_STYLES = "catalog_ids"
    }
}