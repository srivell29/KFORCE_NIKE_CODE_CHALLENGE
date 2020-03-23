package com.example.dictionary.core.utils

import com.example.dictionary.core.exception.NetworkExceptions
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * By convention each [UseCase] implementation will execute its job in a background thread
 * (kotlin coroutine) and will post the searchResults in the UI thread.
 */

abstract class UseCase<out Type, in Params>(
    private val scope: CoroutineScope,
    private val dispatcher: CoroutineDispatcher
)
        where Type : Any {
    abstract suspend fun run(params: Params): Either<NetworkExceptions, Type>
    operator fun invoke(params: Params, onResult: (Either<NetworkExceptions, Type>) -> Unit = {}) {
        scope.launch {
            val result = run(params)

            withContext(dispatcher) {
                onResult(result)
            }

        }
    }
}

