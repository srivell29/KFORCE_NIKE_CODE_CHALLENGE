package com.example.dictionary.dictionaryviews.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dictionary.dictionaryviews.model.DictionaryEntity

@Database(entities = [DictionaryEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun dictionaryDAO(): DictionaryDAO
}