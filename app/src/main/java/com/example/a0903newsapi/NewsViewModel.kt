package com.example.a0903newsapi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a0903newsapi.room.ItemsDao
import com.example.a0903newsapi.room.ItemsEntity
import com.example.a0903newsapi.ui.theme.NewsItem
import com.example.a0903restapi.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {

    private val repository = NewsRepository()
    private val _newsList = MutableStateFlow<List<NewsItem>>(emptyList())
    val newsList: StateFlow<List<NewsItem>> = _newsList

    fun fetchNews(query: String) {
        viewModelScope.launch {
            try {
                val news = repository.searchNews(query)
                _newsList.value = news.items
            }catch(e: Exception){
                e.printStackTrace()
            }
        }
    }
}