package com.userstipa.currency.domain

sealed interface Resource<T : Any> {
    data class Success<T : Any>(val data: T) : Resource<T>
    data class Error<T : Any>(val exception: Throwable) : Resource<T>
    class Loading<T: Any> : Resource<T>
}
