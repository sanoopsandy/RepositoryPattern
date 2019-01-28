package com.repositorydemo.core.base.api

sealed class DataResult<T> {

    data class Progress<T>(var loading: Boolean) : DataResult<T>()
    data class Success<T>(var data: T) : DataResult<T>()
    data class Failure<T>(var error: Throwable) : DataResult<T>()

    companion object {
        fun <T> loading(progress: Boolean): DataResult<T> =
            Progress(progress)
        fun <T> success(data: T): DataResult<T> =
            Success(data)
        fun <T> failure(error: Throwable): DataResult<T> =
            Failure(error)
    }
}