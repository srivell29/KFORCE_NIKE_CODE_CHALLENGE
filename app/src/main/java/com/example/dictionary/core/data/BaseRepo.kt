package com.example.dictionary.core.data

import com.example.dictionary.core.exception.NetworkExceptions
import com.example.dictionary.core.utils.Either

open class BaseRepository {
    suspend fun <R> either(
        data: suspend () -> R
    ): Either<NetworkExceptions, R> {
        return try {
            Either.Right(
                data.invoke()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return Either.Left(NetworkExceptions.NetworkError(RuntimeException("Network issues")))
        }

    }
}