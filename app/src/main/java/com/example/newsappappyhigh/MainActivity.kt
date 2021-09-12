package com.example.newsappappyhigh

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.newsappappyhigh.repository.NewsRepository
import com.example.newsappappyhigh.ui.HomeViewModel
import com.example.newsappappyhigh.ui.HomeViewModelProviderFactory
import com.google.android.gms.ads.MobileAds

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val newsRepository = NewsRepository()
        val viewModelProviderFactory = HomeViewModelProviderFactory(newsRepository)
        viewModel = ViewModelProvider(this,viewModelProviderFactory).get(HomeViewModel::class.java)
    }
}