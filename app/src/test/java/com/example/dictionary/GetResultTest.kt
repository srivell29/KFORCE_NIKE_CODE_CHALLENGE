package com.example.dictionary

import com.example.dictionary.core.utils.Either
import com.example.dictionary.dictionaryviews.data.SearchRepository
import com.example.dictionary.dictionaryviews.model.Result
import com.example.dictionary.dictionaryviews.model.TermsResponse
import com.example.dictionary.dictionaryviews.usecases.GetResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class GetResultTest : UnitTest() {

    private val searchTermsResponse = TermsResponse(
        listOf(
            Result(author = "xyz", word = "test", thumbsDown = 20, thumbsUp = 40),
            Result(author = "alpha", word = "test", thumbsDown = 22, thumbsUp = 60),
            Result(author = "hello", word = "test", thumbsDown = 20, thumbsUp = 50),
            Result(author = "beta", word = "test", thumbsDown = 25, thumbsUp = 70)
        )
    )

    @Mock
    private lateinit var searchRepository: SearchRepository
    private lateinit var getResult: GetResult

    @Before
    fun setUp() {
        getResult = GetResult(searchRepository, TestCoroutineScope(), Dispatchers.IO)
        //return dummy search response form [searchRepository]
        runBlocking {
            `when`(searchRepository.fetchResult("test"))
                .thenReturn(Either.Right(searchTermsResponse.results))
        }

    }

    @Test
    fun `test loading data not Empty`() {
        runBlocking {
            searchRepository.fetchResult("test").either({

            }, {
                assert(it.isNotEmpty())
            })
        }
    }
}