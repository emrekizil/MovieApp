package com.emrekizil.core_model

sealed class ResponseState<out T> {
    data object Loading : ResponseState<Nothing>()
    data class Error(val message: String) : ResponseState<Nothing>()
    data class Success<T>(val data: T) : ResponseState<T>()
}