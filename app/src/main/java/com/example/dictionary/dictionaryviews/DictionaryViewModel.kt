package com.example.dictionary.dictionaryviews

import androidx.lifecycle.MutableLiveData
import com.example.dictionary.core.utils.map
import com.example.dictionary.core.base.BaseViewModel
import com.example.dictionary.dictionaryviews.model.ResultView
import com.example.dictionary.dictionaryviews.usecases.GetResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ViewModel for Dictionary Activity
 */
class DictionaryViewModel constructor(private val getResult: GetResult) : BaseViewModel() {
    val isLoading by lazy { MutableLiveData<Boolean>() }
    val networkExceptions by lazy { MutableLiveData<Exception>() }
    var searchResults: MutableLiveData<List<ResultView>> = MutableLiveData()
    var isAscending = true

    /**
     * Get data from api using use case.
     */
    fun getSearchResults(term: String) {
        isLoading.postValue(true)
        launch(Dispatchers.IO) {
            if (term.isNotEmpty()) {
            getResult.run(GetResult.Param(term))
                .map { list ->
                    list.map { it.toResultView() }
                }.map {
                    if (isAscending) {
                        it.sortedByDescending { resultView -> resultView.thumbsUp }
                    } else {
                        it.sortedByDescending { resultView -> resultView.thumbsDown }
                    }
                }
                .either({
                    isLoading.postValue(false)
                    networkExceptions.postValue(it)
                }, {
                    searchResults.postValue(it)
                    isLoading.postValue(false)
                })
            } else {
                searchResults.postValue(listOf())
                isLoading.postValue(false)
            }
        }

    }

    /**
     *
     */
    fun fetchSortResult(ascending: Boolean): MutableList<ResultView> {
        this.isAscending = ascending
        return if (this.isAscending) {
            searchResults.value.orEmpty().sortedBy { it.thumbsUp }.toMutableList()
        } else {
            searchResults.value.orEmpty().sortedByDescending { it.thumbsUp }.toMutableList()
        }
    }


}