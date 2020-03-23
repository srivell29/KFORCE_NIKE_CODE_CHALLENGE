package com.example.dictionary.dictionaryviews.model

import androidx.recyclerview.widget.DiffUtil

data class ResultView(
    var author: String = "",

    var currentVote: String = "",

    var definition: String = "",

    var example: String = "",

    var thumbsDown: Int = 0,

    var thumbsUp: Int = 0,

    var word: String = "",

    var writtenOn: String = "",

    var defid: Int = 0

)

class SuggestionsViewDiff(
    private val previousList: MutableList<ResultView>,
    private val currentList: MutableList<ResultView>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return (previousList[oldItemPosition].defid == currentList[newItemPosition].defid)
    }

    override fun getOldListSize(): Int = previousList.size

    override fun getNewListSize(): Int = currentList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return (previousList[oldItemPosition] == currentList[newItemPosition])
    }
}