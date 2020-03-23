package com.example.dictionary.dictionaryviews.data

import com.example.dictionary.core.data.BaseRepository
import com.example.dictionary.dictionaryviews.model.Result
import com.example.dictionary.core.exception.NetworkExceptions
import com.example.dictionary.core.utils.Either
import com.example.dictionary.core.base.NetworkHandler
import com.example.dictionary.dictionaryviews.data.datasource.LocalDataSource
import com.example.dictionary.dictionaryviews.data.datasource.RemoteDataSource

class SearchRepositoryImpl
    (
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val networkHandler: NetworkHandler
) : BaseRepository(), SearchRepository {

    override suspend fun fetchResult(term: String): Either<NetworkExceptions, List<Result>> {
        return either {
            //Error handling
            val local = getLocalList(term)
            return@either if (local.isNotEmpty()) {
                local
            } else {
                if (networkHandler.isConnected) getApiList(term)
                    .also { insertResultToDatabase(it) }
                else throw NetworkExceptions.NetworkError(RuntimeException("No offline data available!"))
            }
        }

    }


    private suspend fun insertResultToDatabase(it: List<Result>) {
        localDataSource.addDictionaryEntity(it.map { result -> result.toDictionaryEntity() })
    }

    private suspend fun getApiList(term: String) = remoteDataSource.getTerms(term).results

    private suspend fun getLocalList(term: String) = localDataSource.getDictionaryEntity(term)
        .map { it.toResult() }
}




