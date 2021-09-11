package com.example.newsappappyhigh.repository

import com.example.newsappappyhigh.api.RetrofitInstance
import com.example.newsappappyhigh.models.Article
import com.example.newsappappyhigh.models.NewsResponse
import retrofit2.Response

class NewsRepository() {

    suspend fun getTopHeadLines(countryCode:String,category:String): Response<NewsResponse> {
        return RetrofitInstance.api.getNews(countryCode,category)
    }


}