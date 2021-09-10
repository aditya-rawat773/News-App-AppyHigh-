package com.example.newsappappyhigh.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsappappyhigh.R
import com.example.newsappappyhigh.models.Article
import com.example.newsappappyhigh.models.NewsResponse
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: NewsViewModel
    private lateinit var mHomeAdapter: HomeAdapter
    private lateinit var listData:ArrayList<Article>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel = ViewModelProvider(requireActivity()).get(NewsViewModel::class.java)

        recyclerView.apply {

            mHomeAdapter = HomeAdapter()
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mHomeAdapter
        }

        val countryCode = "in"
        val category = "general"
        homeViewModel.getNewsData(countryCode,category)

        homeViewModel.getPostObserver().observe(viewLifecycleOwner,{

            if (it != null) {

                listData = it.articles as ArrayList<Article>
                mHomeAdapter.setListData(listData)
                mHomeAdapter.notifyDataSetChanged()
            }

        })



    }

}