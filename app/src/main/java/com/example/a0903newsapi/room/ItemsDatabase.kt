package com.example.a0903newsapi.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ItemsEntity::class], version = 1, exportSchema = false)
abstract class ItemsDatabase : RoomDatabase(){
    abstract fun itemsDao(): ItemsDao

    companion object {
        private var instance: ItemsDatabase? = null

        fun getDatabase(context: Context): ItemsDatabase {
            //인스턴스가 null이면 새로운 인스턴스 생성
            return instance ?: synchronized(this) {
                val newInstance = Room.databaseBuilder(
                    context.applicationContext,
                    ItemsDatabase::class.java,
                    "items_database")
                    .build()
                instance = newInstance
                newInstance
            }
        }
    }
}