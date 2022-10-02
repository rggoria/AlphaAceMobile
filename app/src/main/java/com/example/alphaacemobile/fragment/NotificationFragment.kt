package com.example.alphaacemobile.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alphaacemobile.R
import com.example.alphaacemobile.adapter.NotificationAdapter
import com.example.alphaacemobile.databinding.FragmentNotificationBinding
import com.example.alphaacemobile.model.NotificationModel

class NotificationFragment : Fragment() {

    private lateinit var binding: FragmentNotificationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNotificationBinding.inflate(layoutInflater, container, false)

        setupRecyclerView()

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.rvNotificationList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = NotificationAdapter(createNotificationList())
        }
    }

    private fun createNotificationList(): ArrayList<NotificationModel> {
        return arrayListOf<NotificationModel>(
            NotificationModel(R.drawable.ic_coin_white, "BTC", R.drawable.ic_face_white, false),
            NotificationModel(R.drawable.ic_face_white, "ETH", R.drawable.ic_face_white, false)
        )
    }
}