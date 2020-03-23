package com.example.dictionary.core.koin

import com.example.dictionary.dictionaryviews.usecases.GetResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext

/*
Base use cse for clean arch
 */
val useCaseModule = module {
    factory<CoroutineScope> {
        object : CoroutineScope {

            private val job = Job()

            override val coroutineContext: CoroutineContext
                get() = job + Dispatchers.IO
        }
    }

    single<CoroutineDispatcher> { Dispatchers.Main }

    single { GetResult(get(), get(), get()) }
}