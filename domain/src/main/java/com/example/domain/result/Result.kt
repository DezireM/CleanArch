package com.example.domain.result



sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Failed(val message: String) : Result<Nothing>()
    data object Loading: Result<Nothing>()
}