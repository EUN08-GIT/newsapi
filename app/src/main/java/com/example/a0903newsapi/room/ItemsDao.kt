package com.example.a0903newsapi.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemsDao{
    @Query("SELECT * FROM items_table")
    fun getAll(): List<ItemsEntity>

    @Query("DELETE FROM items_table")
    suspend fun clearAll()

    @Query("SELECT * FROM items_table ORDER BY ID DESC")
    fun getAllItems(): Flow<List<ItemsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<ItemsEntity>)
}