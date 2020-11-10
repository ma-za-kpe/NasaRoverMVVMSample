package com.maku.nasarovermvvmsample.data.repo

import com.maku.nasarovermvvmsample.utils.sealed.ResponseState

abstract class BaseRepository {
    companion object {
        private const val UNAUTHORIZED = "Unauthorized"
        private const val NOT_FOUND = "Not found"
        const val SOMETHING_WRONG = "Something went wrong"

        fun <T : Any> handleSuccess(data: T): ResponseState<T> {
            return ResponseState.Success(data)
        }

        fun <T : Any> handleException(code: Int): ResponseState<T> {
            val exception = getErrorMessage(code)
            return ResponseState.Error(Exception(exception))
        }

        private fun getErrorMessage(httpCode: Int): String {
            return when (httpCode) {
                401 -> UNAUTHORIZED
                404 -> NOT_FOUND
                else -> SOMETHING_WRONG
            }
        }
    }
}