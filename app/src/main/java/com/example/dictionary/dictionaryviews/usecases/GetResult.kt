package com.example.dictionary.dictionaryviews.usecases

import com.example.dictionary.dictionaryviews.data.SearchRepository
import com.example.dictionary.dictionaryviews.model.Result
import com.example.dictionary.core.exception.NetworkExceptions
import com.example.dictionary.core.utils.Either
import com.example.dictionary.core.utils.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

/**
 * Search suggestions from api
 */
class GetResult constructor(
    private val repository: SearchRepository,
    scope: CoroutineScope,
    dispatcher: CoroutineDispatcher
) : UseCase<List<Result>, GetResult.Param>(scope, dispatcher) {

    override suspend fun run(params: Param): Either<NetworkExceptions, List<Result>> {
        return repository.fetchResult(params.term)
    }

    data class Param(val term: String)
}