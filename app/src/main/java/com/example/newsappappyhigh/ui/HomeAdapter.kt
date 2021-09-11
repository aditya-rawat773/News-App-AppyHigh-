package com.example.newsappappyhigh.ui

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappappyhigh.R
import com.example.newsappappyhigh.models.Article
import com.example.newsappappyhigh.models.NewsResponse
import kotlinx.android.synthetic.main.news_list.view.*

class HomeAdapter: RecyclerView.Adapter<HomeAdapter.MyViewHolder>()  {

    private var items = ArrayList<Article>()


    fun setListData(data: ArrayList<Article>){
        this.items = data
        Log.d("ankit", "setListData:$items ")

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.MyViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.news_list,parent,false)
        return MyViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: HomeAdapter.MyViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class MyViewHolder(view: View):RecyclerView.ViewHolder(view) {

        val tvItem = view.tv_item

        @SuppressLint("SetTextI18n")
        fun bind(data: Article){
            tvItem.text = data.title
        }
    }
}