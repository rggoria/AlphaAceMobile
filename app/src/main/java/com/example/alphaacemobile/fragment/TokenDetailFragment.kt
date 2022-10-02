package com.example.alphaacemobile.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.alphaacemobile.R
import com.example.alphaacemobile.databinding.FragmentTokenDetailBinding

class TokenDetailFragment : Fragment() {

    private lateinit var binding: FragmentTokenDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTokenDetailBinding.inflate(inflater, container, false)

        binding.apply {

            val tokenName = arguments?.getString("name")

            val tvName = tvTokenDetailHeader

            tvName.text = "$tokenName"

            return root
        }
    }
}