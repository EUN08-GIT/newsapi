package com.example.a0903newsapi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.a0903newsapi.room.ItemsDao
import com.example.a0903restapi.repository.NewsRepository

class RoomDBViewModelFactory(
    private val dao: ItemsDao,
    private val repository: NewsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RoomDBViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RoomDBViewModel(dao, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
