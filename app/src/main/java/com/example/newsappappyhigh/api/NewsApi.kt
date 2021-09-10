package com.example.newsappappyhigh.api

import com.example.newsappappyhigh.models.NewsResponse
import com.example.newsappappyhigh.utils.Utils.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/top-headlines")
    suspend fun getNews(
        @Query("country") countryCode: String = "us",
        @Query("category") category: String = "general",
        @Query("apikey") apikey: String = API_KEY

    ):Response<NewsResponse>
}