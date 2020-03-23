package com.example.dictionary.core.koin

import androidx.room.Room
import com.example.dictionary.dictionaryviews.data.SearchRepository
import com.example.dictionary.dictionaryviews.data.SearchRepositoryImpl
import com.example.dictionary.dictionaryviews.data.datasource.*
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Koin module for data
 */
val dataSourceModule = module {
    single<SearchRepository> {
        SearchRepositoryImpl(
            get(),
            get(),
            get()
        )
    }

    single<RemoteDataSource> { RemoteDataSourceImpl(get()) }
    single<LocalDataSource> { LocalDataSourceImpl(get()) }


}


val databaseModule = module {
    single {
        Room.databaseBuilder(androidContext(), AppDataBase::class.java, "DictionaryAppDB")
            .fallbackToDestructiveMigration()//for testing use only will use migration in production
            .build()
    }
    single { get<AppDataBase>().dictionaryDAO() }

}
