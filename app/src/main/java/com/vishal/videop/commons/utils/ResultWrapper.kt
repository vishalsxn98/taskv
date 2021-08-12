package com.vishal.videop.commons.utils

sealed class ResultWrapper<out T> {
    data class Success<out T>(val data: T) : ResultWrapper<T>()
    data class Failed<out T>(val error: String) :
        ResultWrapper<T>()

    data class Loading<T>(val message: String) : ResultWrapper<T>()
}