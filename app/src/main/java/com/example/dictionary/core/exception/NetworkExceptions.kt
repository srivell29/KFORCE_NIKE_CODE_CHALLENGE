package com.example.dictionary.core.exception

/**
 * Custom Network Exceptions
 */
sealed class NetworkExceptions(val throwable: Throwable) : Exception() {
    class HttpError(throwable: Throwable) : NetworkExceptions(throwable)
    class NetworkError(throwable: Throwable = Throwable("Server Error!")) : NetworkExceptions(throwable)
}