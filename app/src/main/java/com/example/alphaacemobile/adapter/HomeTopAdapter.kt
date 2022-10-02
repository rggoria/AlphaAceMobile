package com.example.alphaacemobile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alphaacemobile.R
import com.example.alphaacemobile.fragment.HomeFragment
import com.example.alphaacemobile.model.HomeTopModel

class HomeTopAdapter(
    val list: ArrayList<HomeTopModel>,
    val listener: HomeFragment
) : RecyclerView.Adapter<HomeTopAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val topRank = itemView.findViewById<TextView>(R.id.tvHomeTopRank)
        val topName = itemView.findViewById<TextView>(R.id.tvHomeTopName)
        val topPrice = itemView.findViewById<TextView>(R.id.tvHomeTopPrice)
        val topMarket = itemView.findViewById<TextView>(R.id.tvHomeTopMarketCap)
        //val topStatistics = itemView.findViewById<ImageView>(R.id.tvHomeTopStatistics)
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                listener.OnClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_top_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        holder.apply {
            topRank.text = item.rank
            topName.text = item.name
            topPrice.text = item.price
            topMarket.text = item.market
            //topStatistics.setImageResource(item.statistics)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}