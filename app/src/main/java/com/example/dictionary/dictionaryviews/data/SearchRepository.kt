package com.example.dictionary.dictionaryviews.data

import com.example.dictionary.dictionaryviews.model.Result
import com.example.dictionary.core.exception.NetworkExceptions
import com.example.dictionary.core.utils.Either

interface SearchRepository {
    suspend fun fetchResult(term: String): Either<NetworkExceptions, List<Result>>
}