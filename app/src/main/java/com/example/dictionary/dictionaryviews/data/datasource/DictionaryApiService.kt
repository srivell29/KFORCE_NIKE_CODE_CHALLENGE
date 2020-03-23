package com.example.dictionary.dictionaryviews.data.datasource

import com.example.dictionary.dictionaryviews.model.TermsResponse
import retrofit2.http.*

interface DictionaryApiService {
    @Headers(
        "x-rapidapi-host: mashape-community-urban-dictionary.p.rapidapi.com",
        "x-rapidapi-key: ad5c8c352bmshcf16184eed3e760p1d24bfjsn9f3ec9b3b743"
    )
    @GET("define")
    suspend fun getTerms(@Query("term") term: String): TermsResponse
}