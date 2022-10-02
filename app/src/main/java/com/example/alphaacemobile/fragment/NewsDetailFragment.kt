package com.example.alphaacemobile.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.alphaacemobile.R
import com.example.alphaacemobile.databinding.FragmentNewsDetailBinding

class NewsDetailFragment : Fragment() {

    private lateinit var binding: FragmentNewsDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewsDetailBinding.inflate(inflater, container, false)

        binding.apply {

            val newsTitle = arguments?.getString("name")
            val newsImage = arguments?.getInt("image")

            val tvTitle = tvNewsDetailTitle
            val imImage = ivNewsDetail

            tvTitle.text = "$newsTitle"
            // need more improvements
            imImage.setImageResource(R.drawable.ic_home)

            return root
        }
    }
}