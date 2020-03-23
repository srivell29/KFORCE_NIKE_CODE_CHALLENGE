package com.example.dictionary.dictionaryviews

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionary.R
import com.example.dictionary.core.exception.NetworkExceptions
import com.example.dictionary.core.extensions.gone
import com.example.dictionary.core.extensions.observeLiveData
import com.example.dictionary.core.extensions.visible
import com.example.dictionary.core.base.BaseActivity
import com.jakewharton.rxbinding3.widget.afterTextChangeEvents
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_dictionary.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

/**
 * Dictionary Activity to show list of search items.
 */
class DictionaryActivity : BaseActivity<DictionaryViewModel>() {
    private val dictionaryViewModel: DictionaryViewModel by viewModel()
    private val searchAdapter = SearchAdapter(mutableListOf())
    //To save search disposable values
    private val searchCompositeDisposable: CompositeDisposable = CompositeDisposable()


    override fun getViewModel(): DictionaryViewModel {
        return dictionaryViewModel
    }

    override fun onResume() {
        super.onResume()
        subscribeSearchText()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dictionary)
        initObservers()
    }

    /**
     * Observe searchResults LiveData from viewModel
     */
    private fun initObservers() {
        rv_SearchSuggestions.layoutManager = LinearLayoutManager(this@DictionaryActivity)
        rv_SearchSuggestions.adapter = searchAdapter

        observeLiveData(dictionaryViewModel.searchResults) {
            it?.apply {
                if (this.isEmpty()) {
                    groupError.visible()
                    if (et_search.text.toString().isNotEmpty())
                        tv_Error.text = getString(R.string.dictionary_activity_no_data_found)
                    else
                        tv_Error.text = getString(R.string.dictionary_activity_no_input_found)
                } else {
                    rv_SearchSuggestions.visible()
                    groupError.gone()
                }
                searchAdapter.updateDateList(this.toMutableList())
            } ?: kotlin.run {
                searchAdapter.updateDateList(mutableListOf())
            }
        }

        observeLiveData(dictionaryViewModel.isLoading) {
            it?.apply {
                if (this) {
                    progress_bar.visible()
                } else {
                    progress_bar.gone()
                }
            }
        }
        handleExceptions()
    }

    /**
     * handle Network Exceptions
     */
    private fun handleExceptions() {

        observeLiveData(dictionaryViewModel.networkExceptions) {
            dictionaryViewModel.isLoading.value = false
            it?.apply {
                if (it is NetworkExceptions) {
                    when (it) {
                        is NetworkExceptions.NetworkError -> {
                            rv_SearchSuggestions.gone()
                            groupError.visible()
                            tv_Error.text = getString(R.string.connection_error)
                        }
                        is NetworkExceptions.HttpError -> {//will not occur
                            Log.d("Http Error", it.throwable.localizedMessage.orEmpty())
                        }
                    }

                } else {
                    Log.e("Error", it.localizedMessage, it)
                }
                dictionaryViewModel.networkExceptions.value = null
            }
        }
    }

    /**
     * Initialize Views
     */
    private fun subscribeSearchText() {
        et_search.afterTextChangeEvents()
            .skipInitialValue()
            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribe({
                dictionaryViewModel.getSearchResults(et_search.text.toString())
            }, {
                it.printStackTrace()
            }).apply {
                searchCompositeDisposable.add(this)
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_dictionary_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.actionSortUp -> {
                sortByUpVotes()
                true
            }
            R.id.actionSortDown -> {
                sortByDownVotes()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * sort by up votes
     */
    private fun sortByUpVotes() {
        searchAdapter.updateDateList(dictionaryViewModel.fetchSortResult(false))
        scrollToZero()
    }

    /**
     * Scroll to top when sort
     */
    private fun scrollToZero() {
        rv_SearchSuggestions.post {
            rv_SearchSuggestions.smoothScrollToPosition(0)
        }
    }

    /**
     * sort by down votes
     */
    private fun sortByDownVotes() {
        searchAdapter.updateDateList(dictionaryViewModel.fetchSortResult(true))
        scrollToZero()
    }

    override fun onPause() {
        dictionaryViewModel.networkExceptions.value = null
        searchCompositeDisposable.clear()
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        searchCompositeDisposable.dispose()
    }

}
