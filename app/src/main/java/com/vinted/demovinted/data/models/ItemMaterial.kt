package com.vinted.demovinted.data.models

data class ItemMaterial(
    val id: String = "",
    val title: String = ""
) {

    override fun toString(): String {
        return title
    }
}
