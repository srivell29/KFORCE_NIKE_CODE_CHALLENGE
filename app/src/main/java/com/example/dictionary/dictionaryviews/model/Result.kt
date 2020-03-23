package com.example.dictionary.dictionaryviews.model


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("author")
    var author: String = "",
    @SerializedName("current_vote")
    var currentVote: String = "",
    @SerializedName("defid")
    var defid: Int = 0,
    @SerializedName("definition")
    var definition: String = "",
    @SerializedName("example")
    var example: String = "",
    @SerializedName("permalink")
    var permalink: String = "",
    @SerializedName("sound_urls")
    var soundUrls: List<String> = listOf(),
    @SerializedName("thumbs_down")
    var thumbsDown: Int = 0,
    @SerializedName("thumbs_up")
    var thumbsUp: Int = 0,
    @SerializedName("word")
    var word: String = "",
    @SerializedName("written_on")
    var writtenOn: String = ""
) {
    fun toDictionaryEntity() = DictionaryEntity(
        author,
        currentVote,
        defid,
        definition,
        example,
        permalink,
        thumbsDown,
        thumbsUp,
        word,
        writtenOn
    )

    fun toResultView() = ResultView(
        author,
        currentVote,
        definition,
        example,
        thumbsDown,
        thumbsUp,
        word,
        writtenOn,
        defid
    )
}