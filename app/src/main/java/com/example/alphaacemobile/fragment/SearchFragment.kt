package com.example.alphaacemobile.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.alphaacemobile.R
import com.example.alphaacemobile.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: ArrayAdapter<String>

    val tokenList =
        listOf(
            "ETH",
            "ERC",
            "RAM",
            "PEJ",
            "SHI",
            "HI",
            "HELLO",
            "PRO",
            "LOW",
            "FLO"
        )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        setupListView()
        setupSearchView()

        return binding.root
    }

    private fun setupListView() {
        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, tokenList)
        binding.lvSearch.adapter = adapter
        binding.lvSearch.setOnItemClickListener { parent, view, position, id ->
            val element = adapter.getItem(position)

            val bundle = Bundle()
            bundle.putString("name", element)
            Toast.makeText(requireActivity(), "Clicked on token: ${element}", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.tokenDetailFragment, bundle)
        }
    }

    private fun setupSearchView() {
        binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val isMatchFound = tokenList.contains(query)
                val msg = if (isMatchFound) "Found: $query" else getString(R.string.no_match)
                Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
    }
}