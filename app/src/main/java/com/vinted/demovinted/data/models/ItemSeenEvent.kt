package com.vinted.demovinted.data.models

data class ItemSeenEvent(
    val timestamp: Long,
    val itemId: Int
) {
}