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
import okhttp3.internal.notify

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
        homeViewModel.getNewsData("in","general")

        toggleButton.addOnButtonCheckedListener { toggleButton, checkedId, isChecked ->
            if(isChecked){
                when(checkedId){
                    R.id.button1 ->{
                        getCategory("in","general")
                    }
                    R.id.button7 -> {
                        getCategory("in","entertainment")
                    }
                    R.id.button2 ->{
                        getCategory("in","health")
                    }
                    R.id.button3-> {
                        getCategory("in","science")
                    }
                    R.id.button4 ->{
                        getCategory("in","sports")
                    }
                    R.id.button5 -> {
                        getCategory("in","technology")
                    }
                    R.id.button6 ->{
                        getCategory("in","business")
                    }
                }
            }
        }




        recyclerView.apply {

            mHomeAdapter = HomeAdapter()
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mHomeAdapter
        }




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

    @SuppressLint("NotifyDataSetChanged")
    fun getCategory(countryCode:String, category:String){

        homeViewModel.getNewsData(countryCode,category)
        mHomeAdapter.notifyDataSetChanged()
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

}