package com.vinted.demovinted.data.models


data class ItemStatus(
    val id: String = "",
    val title: String = "",
    val description: String? = null,
    val explanation: String? = null,
    val isDefault: Boolean = false,
    val requiresAcquiringInformation: Boolean = false
)  {
    override fun toString(): String {
        return title
    }
}
