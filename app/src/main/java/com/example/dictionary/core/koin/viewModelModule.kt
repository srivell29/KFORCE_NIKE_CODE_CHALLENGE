package com.example.dictionary.core.koin

import com.example.dictionary.dictionaryviews.DictionaryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * View module Kotin DI
 */
val viewModelModule = module {
    viewModel {
        DictionaryViewModel(get())
    }
}