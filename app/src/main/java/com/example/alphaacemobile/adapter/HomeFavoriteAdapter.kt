package com.example.alphaacemobile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alphaacemobile.model.HomeFavoriteModel
import com.example.alphaacemobile.databinding.HomeFavoriteItemBinding

class HomeFavoriteAdapter (
    private val homeFavoriteList : ArrayList<HomeFavoriteModel>,
    private val listener: (HomeFavoriteModel, Int) -> Unit
) : RecyclerView.Adapter<HomeFavoriteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = HomeFavoriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(homeFavoriteList[position])
        holder.itemView.setOnClickListener{
            listener(homeFavoriteList[position], position)
        }
    }

    override fun getItemCount(): Int {
        return homeFavoriteList.size
    }

    class ViewHolder(var itemBinding: HomeFavoriteItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindItem(favorite: HomeFavoriteModel) {
            itemBinding.tvHomeFavoriteName.text = favorite.name
            itemBinding.tvHomeFavoritePrice.text = favorite.price
        }
    }
}