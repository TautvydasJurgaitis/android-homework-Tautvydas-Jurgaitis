package com.vinted.demovinted.data.network.responses

class ApiValidationError(val field: String, val value: String) : RuntimeException() {
    override fun toString(): String {
        return "ApiValidationError(value: $value; field: $field)"
    }
}
