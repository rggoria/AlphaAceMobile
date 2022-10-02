package com.example.alphaacemobile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alphaacemobile.databinding.HomeFavoriteItemBinding
import com.example.alphaacemobile.databinding.HomeNewsItemBinding
import com.example.alphaacemobile.model.HomeFavoriteModel
import com.example.alphaacemobile.model.HomeNewsModel

class HomeNewsAdapter(
    private val homeNewsList: ArrayList<HomeNewsModel>,
    private val listener: (HomeNewsModel, Int) -> Unit
) : RecyclerView.Adapter<HomeNewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = HomeNewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(homeNewsList[position])
        holder.itemView.setOnClickListener {
            listener(homeNewsList[position], position)
        }
    }

    override fun getItemCount(): Int {
        return homeNewsList.size
    }

    class ViewHolder(var itemBinding: HomeNewsItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindItem(news: HomeNewsModel) {
            itemBinding.ivHomeNewsImage.setImageResource(news.image)
            itemBinding.tvHomeNewsTitle.text = news.title
            itemBinding.tvHomeNewsDescription.text = news.description
        }
    }
}