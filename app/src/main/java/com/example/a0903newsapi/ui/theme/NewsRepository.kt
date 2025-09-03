package com.example.a0903restapi.repository

import com.example.a0903newsapi.ui.theme.NaverNewsDTO

class NewsRepository{
    suspend fun searchNews(query: String): NaverNewsDTO {
        return RetrofitInterface.api.searchNews(query)
    }
}