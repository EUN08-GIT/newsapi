package com.example.a0903newsapi.ui.theme

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NaverNewsApi {
    @Headers(
        "X-Naver-Client-Id:iYdRXOu7AhRwgtvII3aK ",
        "X-Naver-Client-Secret: IEiRckmOzB"
    )
    @GET("v1/search/news.json")
    suspend fun searchNews(
        @Query("query") query: String,
        @Query("display") display: Int = 10,
        @Query("start") start: Int = 1,
        @Query("sort") sort: String = "sim"
    ): NaverNewsDTO
}
