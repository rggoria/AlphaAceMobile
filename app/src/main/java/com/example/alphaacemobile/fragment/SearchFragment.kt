package com.example.alphaacemobile.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.alphaacemobile.Constant
import com.example.alphaacemobile.R
import com.example.alphaacemobile.databinding.FragmentSearchBinding
import org.json.JSONObject

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        val url = Constant.BASE_URL + "get_top_token_home"
        val requestQueue = Volley.newRequestQueue(requireActivity())
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener { response ->
                val jsonObject = JSONObject(response)
                if (jsonObject.get("response").equals("Success")){
                    val jsonArray = jsonObject.getJSONArray("data")
                    // Log.d("json", jsonArray.toString())

                    for (i in 0..jsonArray.length()-1) {
                        val jo = jsonArray.getJSONObject(i)
                        //store your variable
                        val token_name = jo.getString("token_name").toString()

//                      Log.d("jarray", jsonArray.toString())
                        Log.d("tk_name", token_name)
                        val tokenList = listOf(token_name)
//                        Toast.makeText(requireActivity(), "$tokenList",Toast.LENGTH_SHORT).show()
                        Log.d("hi", token_name)
                        setupListView(tokenList)
                        setupSearchView(tokenList)
                    }


                }else{
                    Toast.makeText(requireActivity(),jsonObject.get("response").toString(),Toast.LENGTH_SHORT).show()
                }

            }, Response.ErrorListener { error ->
                Toast.makeText(requireActivity(),error.toString(), Toast.LENGTH_SHORT).show()
            })
        requestQueue.add(stringRequest)

        return binding.root
    }

    private fun setupListView(tokenList: List<String>) {
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

    private fun setupSearchView(tokenList: List<String>) {
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