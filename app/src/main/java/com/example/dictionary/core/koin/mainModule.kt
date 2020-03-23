package com.example.dictionary.core.koin

import com.example.dictionary.core.base.NetworkHandler
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Main module for Koin dependency injection
 */
val mainModule = module {
    single { NetworkHandler((androidContext())) }
}