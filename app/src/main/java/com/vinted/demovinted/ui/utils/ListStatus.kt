package com.vinted.demovinted.ui.utils

sealed class ListStatus
data class Loading(val loading: Boolean = false) : ListStatus()
data class Success<T : Any>(var data: T) : ListStatus()
data class Error(val t: Throwable) : ListStatus()