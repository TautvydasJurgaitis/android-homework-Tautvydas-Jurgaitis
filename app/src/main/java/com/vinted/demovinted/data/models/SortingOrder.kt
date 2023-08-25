package com.vinted.demovinted.data.models

enum class SortingOrder(val key: String) {
    RELEVANCE("relevance"),
    PRICE_HIGH_TO_LOW("price_high_to_low"),
    PRICE_LOW_TO_HIGH("price_low_to_high"),
    UPLOAD_DATE("newest_first");

    companion object {
        val CATALOG_SORTING_ORDERS = listOf(
            RELEVANCE,
            PRICE_HIGH_TO_LOW,
            PRICE_LOW_TO_HIGH,
            UPLOAD_DATE
        )
    }
}