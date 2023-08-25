package com.vinted.demovinted.data.models


data class ItemColor(
        val id: String = "",
        val hex: String? = null,
        val code: String? = "",
        val title: String = "",
        val order: Int = 0
) : Comparable<ItemColor?> {

    override fun compareTo(other: ItemColor?): Int {
        if (other != null) {
            return order - other.order
        }
        return 0
    }

    override fun toString() = title
}
