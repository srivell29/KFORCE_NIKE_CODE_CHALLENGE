package com.example.dictionary.dictionaryviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.R
import com.example.dictionary.dictionaryviews.SearchAdapter.ViewHolder
import com.example.dictionary.dictionaryviews.model.ResultView
import com.example.dictionary.dictionaryviews.model.SuggestionsViewDiff
import kotlinx.android.synthetic.main.item_search.view.*

/**
 * Recycler view adapter to search Suggestions
 */
class SearchAdapter(private var result: MutableList<ResultView>) :
    RecyclerView.Adapter<ViewHolder>() {

    /**
     * Update list data
     */
    fun updateDateList(newSuggestions: MutableList<ResultView>) {
        val diffCallBack = SuggestionsViewDiff(result, newSuggestions)

        val diffResult = DiffUtil.calculateDiff(diffCallBack)

        this.result.clear()
        this.result.addAll(newSuggestions)

        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_search,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = result.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            result[position].apply {
                tv_search_word.text = word
                tv_search_word_definition.text = definition
                tv_likes_count.text = thumbsUp.toString()
                tv_dis_likes_count.text = thumbsDown.toString()
            }
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}