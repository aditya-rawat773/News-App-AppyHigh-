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
import com.example.newsappappyhigh.databinding.FragmentHomeBinding
import com.example.newsappappyhigh.models.Article
import com.example.newsappappyhigh.utils.Resource
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.fragment_home.*
import okhttp3.internal.notify

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var mHomeAdapter: HomeAdapter


    private var list = ArrayList<Article>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.adView.loadAd(AdRequest.Builder().build())

        homeViewModel = (activity as MainActivity).viewModel

        binding.recyclerView.apply {

            mHomeAdapter = HomeAdapter(list)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mHomeAdapter
        }

        getCategory("in","general")

        var country = "in"
        var category = "general"


        binding.toggleButtonCc.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if(isChecked){
                when(checkedId){
                    R.id.btn_in ->  {
                        getCategory("in",category)
                        country = "in"
                    }
                    R.id.btn_us -> {
                        getCategory("us",category)
                        country = "us"
                    }
                }
            }
        }


        binding.toggleButton.addOnButtonCheckedListener { toggleButton, checkedId, isChecked ->
            if(isChecked){
                when(checkedId){

                    R.id.button1 ->{
                        category = "general"
                        getCategory(country,category)

                    }
                    R.id.button7 -> {
                        category = "entertainment"
                        getCategory(country,category)

                    }
                    R.id.button2 ->{
                        category="health"
                        getCategory(country,category)

                    }
                    R.id.button3-> {
                        category = "science"
                        getCategory(country,category)
                    }
                    R.id.button4 ->{
                        category = "sports"
                        getCategory(country,category)
                    }
                    R.id.button5 -> {
                        category = "technology"
                        getCategory(country,category)
                    }
                    R.id.button6 ->{
                        category = "business"
                        getCategory(country,category)
                    }
                }
            }
        }

        mHomeAdapter.setOnItemClickListener {

            val bundle = Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(R.id.action_homeFragment_to_articleFragment2,bundle)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun getCategory(countryCode:String, category:String){

        homeViewModel.getNewsData(countryCode,category)
        homeViewModel.getPostObserver().observe(viewLifecycleOwner,{

            when(it){
                is Resource.Success ->{
                    hideProgressBar()
                    it.data?.let { newsResponse ->


                        list.clear()
                        list.addAll(newsResponse.articles)

                        Log.d("adi", "onViewCreated: ${newsResponse.articles}")
                       // mHomeAdapter.setListData(newsResponse.articles as ArrayList<Article>)
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