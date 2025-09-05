package com.example.a0903newsapi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a0903newsapi.room.ItemsDao
import com.example.a0903newsapi.room.ItemsEntity
import com.example.a0903restapi.repository.NewsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RoomDBViewModel(
    private val dao: ItemsDao,
    private val repository: NewsRepository
) : ViewModel() {

    // DB Flow를 StateFlow로 변환 → UI에서 collectAsState() 가능
    val newsList: StateFlow<List<ItemsEntity>> =
        dao.getAllItems().stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            emptyList()
        )

    // API 호출 → Entity 변환 → DB 저장
    fun fetchNews(query: String) {
        viewModelScope.launch {
            try {
                val news = repository.searchNews(query)

                // 1. API DTO → Room Entity 변환
                val entities = news.items.map { dto ->
                    ItemsEntity(
                        title = dto.title,
                        originallink = dto.originallink,
                        link = dto.link,
                        description = dto.description,
                        pubDate = dto.pubDate
                    )
                }

                // 2. DB 저장
                dao.clearAll()          // 기존 데이터 삭제
                dao.insertAll(entities) // 새 데이터 저장

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
