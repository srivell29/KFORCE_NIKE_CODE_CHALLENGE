package com.example.dictionary.dictionaryviews.data.datasource

import com.example.dictionary.dictionaryviews.model.DictionaryEntity
import com.example.dictionary.dictionaryviews.model.TermsResponse
import retrofit2.http.Query

interface RemoteDataSource {
    suspend fun getTerms(@Query("term") term: String): TermsResponse
}

interface LocalDataSource {
    suspend fun addDictionaryEntity(dictionaryEntity: DictionaryEntity)
    suspend fun addDictionaryEntity(dictionaryEntity: List<DictionaryEntity>)
    suspend fun getDictionaryEntity(term: String): List<DictionaryEntity>
}

class RemoteDataSourceImpl(private val apiService: DictionaryApiService) : RemoteDataSource {
    override suspend fun getTerms(term: String): TermsResponse {
        return apiService.getTerms(term)
    }

}

class LocalDataSourceImpl(private val dictionaryDAO: DictionaryDAO) : LocalDataSource {
    override suspend fun addDictionaryEntity(dictionaryEntity: DictionaryEntity) {
        return dictionaryDAO.insertDictionary(dictionaryEntity)
    }

    override suspend fun addDictionaryEntity(dictionaryEntity: List<DictionaryEntity>) {
        return dictionaryDAO.insertDictionary(dictionaryEntity)
    }

    override suspend fun getDictionaryEntity(term: String): List<DictionaryEntity> {
        return dictionaryDAO.findResult(term)
    }

}

