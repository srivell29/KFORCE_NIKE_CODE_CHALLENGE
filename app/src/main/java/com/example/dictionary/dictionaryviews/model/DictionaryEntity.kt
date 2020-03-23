package com.example.dictionary.dictionaryviews.model


import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "Dictionary", primaryKeys = ["defid"])
data class DictionaryEntity(
    @ColumnInfo(name = "author")
    var author: String = "",
    @ColumnInfo(name = "current_vote")
    var currentVote: String = "",
    @ColumnInfo(name = "defid")
    var defid: Int = 0,
    @ColumnInfo(name = "definition")
    var definition: String = "",
    @ColumnInfo(name = "example")
    var example: String = "",
    @ColumnInfo(name = "permalink")
    var permalink: String = "",
    /*@ColumnInfo(name = "sound_urls")
    var soundUrls: List<String> = listOf(),*/
    @ColumnInfo(name = "thumbs_down")
    var thumbsDown: Int = 0,
    @ColumnInfo(name = "thumbs_up")
    var thumbsUp: Int = 0,
    @ColumnInfo(name = "word")
    var word: String = "",
    @ColumnInfo(name = "written_on")
    var writtenOn: String = ""
) {
    fun toResult() = Result(
        author,
        currentVote,
        defid,
        definition,
        example,
        permalink,
        arrayListOf(),
        thumbsDown,
        thumbsUp,
        word,
        writtenOn
    )
}