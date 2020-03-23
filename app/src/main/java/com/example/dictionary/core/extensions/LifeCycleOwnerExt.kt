package com.example.dictionary.core.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.dictionary.core.exception.NetworkExceptions

/**
 * Observer extensions for easy use
 */
fun <T : Any, L : LiveData<T>> LifecycleOwner.observeLiveData(liveData: L, body: (T?) -> Unit) =
    liveData.observe(this, Observer(body))

fun <L : LiveData<NetworkExceptions>> LifecycleOwner.failure(
    liveData: L,
    body: (NetworkExceptions?) -> Unit
) =
    liveData.observe(this, Observer(body))