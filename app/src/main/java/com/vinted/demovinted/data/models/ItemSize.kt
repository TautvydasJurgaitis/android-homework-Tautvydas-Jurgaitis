package com.vinted.demovinted.data.models

data class ItemSize(
   val id: String = "",
    val title: String = "",
    val isDefault: Boolean = false,
    val isOther: Boolean = false,
    val order: Int = 0,
    val faqEntryId: Int = 0
) : Comparable<ItemSize> {

    override fun compareTo(other: ItemSize): Int {
        return order - other.order
    }

    override fun toString(): String {
        return title
    }
}
