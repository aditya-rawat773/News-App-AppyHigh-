package com.example.newsappappyhigh.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsappappyhigh.models.Article
import com.example.newsappappyhigh.models.NewsResponse
import com.example.newsappappyhigh.repository.NewsRepository
import com.example.newsappappyhigh.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeViewModel(
    private val newsRepository : NewsRepository
): ViewModel() {

    private val newsData:MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var homeNewsResponse: NewsResponse? = null

    fun getPostObserver():MutableLiveData<Resource<NewsResponse>>{
        return newsData
    }

    fun getNewsData(countryCode:String,category:String){
        newsData.postValue(Resource.Loading())
        viewModelScope.launch (Dispatchers.IO) {
            val newsResponse = newsRepository.getTopHeadLines(countryCode, category)
            newsData.postValue(handleNewsResponse(newsResponse))
        }
    }

    private fun handleNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse>? {
        if (response.isSuccessful){
            response.body()?.let {
                if(homeNewsResponse == null){
                    homeNewsResponse = it
                } else {
                    val oldArticles = homeNewsResponse?.articles
                    oldArticles?.clear()
                    val newArticle = it.articles
                    oldArticles?.addAll(newArticle)
                }
                return Resource.Success(homeNewsResponse ?: it)
            }
        }
        return Resource.Error(response.message())
    }
}