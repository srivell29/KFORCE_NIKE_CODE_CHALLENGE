package com.example.dictionary.dictionaryviews.data.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dictionary.dictionaryviews.model.DictionaryEntity


@Dao
interface DictionaryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDictionary(dictionaryEntity: DictionaryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDictionary(dictionaryEntity: List<DictionaryEntity>)

    @Query("SELECT * FROM dictionary  WHERE word LIKE :term")
    suspend fun findResult(term: String): List<DictionaryEntity>
}