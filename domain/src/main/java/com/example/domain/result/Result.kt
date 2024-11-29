package com.example.domain.result

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Failed(val message: String) : Result<Nothing>()
    object Loading : Result<Nothing>()
}
