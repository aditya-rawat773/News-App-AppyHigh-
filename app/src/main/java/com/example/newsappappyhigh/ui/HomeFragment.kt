package com.example.newsappappyhigh.ui

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsappappyhigh.MainActivity
import com.example.newsappappyhigh.R
import com.example.newsappappyhigh.models.Article
import com.example.newsappappyhigh.utils.Resource
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var mHomeAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        homeViewModel = (activity as MainActivity).viewModel

        recyclerView.apply {

            mHomeAdapter = HomeAdapter()
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mHomeAdapter
        }

        val countryCode = "in"
        val category = "general"
        homeViewModel.getNewsData(countryCode,category)

        mHomeAdapter.setOnItemClickListener {

            val bundle = Bundle().apply {
                putSerializable("article",it)
            }

            findNavController().navigate(R.id.action_homeFragment_to_articleFragment2,bundle)
        }

        homeViewModel.getPostObserver().observe(viewLifecycleOwner,{

            when(it){
                is Resource.Success ->{
                    hideProgressBar()
                    it.data?.let { newsResponse ->
                        Log.d("adi", "onViewCreated: ${newsResponse.articles}")
                        mHomeAdapter.setListData((newsResponse.articles as ArrayList<Article>))
                        mHomeAdapter.notifyDataSetChanged()

                    }
                }
                is Resource.Error ->{
                    hideProgressBar()
                    it.message?.let{ message ->

                        Log.e(TAG,"An error occur $message")
                    }
                }
                is Resource.Loading ->{
                    showProgressBar()
                }
            }
        })



    }

    private fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE

    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE

    }

}