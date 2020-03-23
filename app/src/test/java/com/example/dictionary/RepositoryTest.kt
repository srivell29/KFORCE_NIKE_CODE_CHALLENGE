package com.example.dictionary

import com.example.dictionary.core.exception.NetworkExceptions
import com.example.dictionary.core.base.NetworkHandler
import com.example.dictionary.dictionaryviews.data.SearchRepository
import com.example.dictionary.dictionaryviews.data.SearchRepositoryImpl
import com.example.dictionary.dictionaryviews.data.datasource.LocalDataSource
import com.example.dictionary.dictionaryviews.data.datasource.RemoteDataSource
import com.example.dictionary.dictionaryviews.model.Result
import com.example.dictionary.dictionaryviews.model.TermsResponse
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class RepositoryTest : UnitTest() {


    private val termsResponse = TermsResponse(
        listOf(
            Result(author = "xyz", word = "test", thumbsDown = 20, thumbsUp = 40),
            Result(author = "alpha", word = "test", thumbsDown = 22, thumbsUp = 60),
            Result(author = "beta", word = "test", thumbsDown = 25, thumbsUp = 70),
            Result(author = "hello", word = "test", thumbsDown = 20, thumbsUp = 50)

        )
    )

    private lateinit var searchRepository: SearchRepository
    @Mock
    lateinit var localDataSource: LocalDataSource
    @Mock
    lateinit var remoteDataSource: RemoteDataSource
    @Mock
    private lateinit var networkHandler: NetworkHandler


    @Before
    fun setUp() {
        searchRepository = SearchRepositoryImpl(remoteDataSource, localDataSource, networkHandler)
        runBlocking {
            Mockito.`when`(localDataSource.getDictionaryEntity("test"))
                .thenReturn(arrayListOf())
            Mockito.`when`(remoteDataSource.getTerms("test"))
                .thenReturn(termsResponse)
        }

    }

    @Test
    fun `test network connected`() {
        Mockito.`when`(networkHandler.isConnected)
            .thenReturn(true)
        runBlocking {
            searchRepository.fetchResult("test").either({
            }, {
                assert(it.isNotEmpty())
            })
        }
    }

    @Test
    fun `test network disconnected`() {
        Mockito.`when`(networkHandler.isConnected)
            .thenReturn(true)

        runBlocking {
            searchRepository.fetchResult("test").either({
                assert(it is NetworkExceptions.NetworkError)

            }, {
            })
        }
    }


}