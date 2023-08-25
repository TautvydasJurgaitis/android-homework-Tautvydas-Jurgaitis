package com.vinted.demovinted.data.models

import java.io.Serializable

data class ItemStyle(
    val id: String = "",
    val name: String = "",
    val children: List<ItemStyle> = emptyList(),
    val parentId: String? = null,
    val rootCategoryId: String? = null
) : Serializable
