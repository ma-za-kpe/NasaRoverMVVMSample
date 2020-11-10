package com.maku.nasarovermvvmsample.utils.sealed


sealed class ResponseState<out T : Any> {
    data class Success<out T : Any>(val data: T) : ResponseState<T>()
    data class Error(val exception: Exception) : ResponseState<Nothing>()
    object InProgress : ResponseState<Nothing>()

    val extractData: T?
        get() = when (this) {
            is Success -> data
            is Error -> null
            is InProgress -> null
        }
}