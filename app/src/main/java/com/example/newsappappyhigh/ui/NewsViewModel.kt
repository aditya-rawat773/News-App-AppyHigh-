package com.example.newsappappyhigh.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsappappyhigh.models.NewsResponse
import com.example.newsappappyhigh.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel: ViewModel() {
    private val newsRepository:NewsRepository= NewsRepository()
    private val newsData:MutableLiveData<NewsResponse> = MutableLiveData()


    fun getPostObserver():MutableLiveData<NewsResponse>{
        return newsData
    }

    fun getNewsData(countryCode:String,category:String){

        viewModelScope.launch (Dispatchers.IO) {
            val newsResponse = NewsRepository().getTopHeadLines(countryCode, category)
            newsData.postValue(newsResponse.body())
        }


    }
}