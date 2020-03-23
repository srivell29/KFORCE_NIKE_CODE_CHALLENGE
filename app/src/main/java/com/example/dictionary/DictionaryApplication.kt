package com.example.dictionary

import android.app.Application
import com.example.dictionary.core.koin.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * Base Application class to initiate Koin setup
 *
 */
class DictionaryApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        //Init koin (dependency Injection frame for kotlin)
        startKoin {
            androidContext(this@DictionaryApplication.applicationContext)
            androidLogger(Level.DEBUG)
            modules(
                listOf(
                    viewModelModule,
                    networkModule,
                    dataSourceModule,
                    mainModule,
                    databaseModule,
                    useCaseModule
                )
            )
        }
    }
}