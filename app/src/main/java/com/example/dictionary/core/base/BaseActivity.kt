package com.example.dictionary.core.base

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity<T : BaseViewModel> : AppCompatActivity() {

    private lateinit var baseViewModel: T

    abstract fun getViewModel(): T

    override fun onResume() {
        super.onResume()
        baseViewModel = getViewModel()
    }
}