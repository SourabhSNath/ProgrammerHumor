package dev.sourabh.programmerhumor.data.remote

import java.io.IOException

sealed class Result<out T> {

    object Empty : Result<Nothing>()
    object Loading : Result<Nothing>()
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable) : Result<Nothing>() {
        val isNetworkError: Boolean get() = exception is IOException
    }

    companion object {
        fun <T> success(data: T) = Success(data)
        fun error(exception: Throwable) = Error(exception)
        fun empty() = Empty
        fun loading() = Loading
        fun <T> successOrEmpty(list: List<T>): Result<List<T>> {
            return if (list.isEmpty()) Empty else Success(list)
        }
    }
}