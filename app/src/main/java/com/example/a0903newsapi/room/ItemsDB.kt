package com.example.a0903newsapi.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items_table")
data class ItemsEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title : String,
    val originallink : String,
    val link : String,
    val description : String,
    val pubDate : String
)