package com.example.alphaacemobile.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.alphaacemobile.Constant.BASE_URL
import com.example.alphaacemobile.R
import com.example.alphaacemobile.adapter.HomeFavoriteAdapter
import com.example.alphaacemobile.adapter.HomeNewsAdapter
import com.example.alphaacemobile.adapter.HomeTopAdapter
import com.example.alphaacemobile.databinding.FragmentHomeBinding
import com.example.alphaacemobile.model.HomeFavoriteModel
import com.example.alphaacemobile.model.HomeNewsModel
import com.example.alphaacemobile.model.HomeTopModel
import org.json.JSONObject

private lateinit var binding: FragmentHomeBinding

class HomeFragment : Fragment() {


    private var list: ArrayList<HomeTopModel> = ArrayList()
    private var rcvAdapter = HomeTopAdapter(list, this@HomeFragment)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        getData()
        setupRecyclerView()
        binding.sflHome.setOnRefreshListener {
            getData()
            binding.sflHome.isRefreshing = false
        }
        return binding.root
    }

    private fun initView() {

    }

    private fun getData() {
        val url = BASE_URL + "get_top_token_home"
        val requestQueue = Volley.newRequestQueue(requireActivity())
        val stringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener { response ->
                list.clear()  //Use to block the repetition
                val jsonObject = JSONObject(response)
                if (jsonObject.get("response").equals("Success")){
                    val jsonArray = jsonObject.getJSONArray("data")
                    // Log.d("json", jsonArray.toString())

                    for (i in 0..jsonArray.length()-1) {
                        val jo = jsonArray.getJSONObject(i)
                        //store your variable
                        val top = jo.getString("top").toString()
                        val token_name = jo.getString("token_name").toString()
                        val curr_prc = jo.getString("curr_prc").toString()
                        val cir_supp = jo.getString("cir_supp").toString()

//                      Log.d("jarray", jsonArray.toString())
                        Log.d("top", top)
                        Log.d("tk_name", token_name)
                        Log.d("tk_price", curr_prc)
                        Log.d("tk_price", cir_supp)

                        val user = HomeTopModel(top, token_name, curr_prc, cir_supp, R.drawable.ic_home)
                        list.add(user)
                    }

                }else{
                    Toast.makeText(requireActivity(),jsonObject.get("response").toString(),Toast.LENGTH_SHORT).show()
                }

            }, Response.ErrorListener { error ->
                Toast.makeText(requireActivity(),error.toString(), Toast.LENGTH_SHORT).show()
            })
        requestQueue.add(stringRequest)


    }

    fun OnClick(position: Int) {
        val bundle = Bundle()
        bundle.putString("name", list[position].name)
        Toast.makeText(requireActivity(), "Clicked on token: ${list[position].name}", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.tokenDetailFragment, bundle)
    }


    private fun setupRecyclerView() {
        binding.rvHomeFavoriteList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = HomeFavoriteAdapter(createHomeFavoriteList()){
                    favorite, _ ->
                val bundle = Bundle()
                bundle.putString("name", favorite.name)
                Toast.makeText(requireActivity(), "Clicked on token: ${favorite.name}", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.tokenDetailFragment, bundle)
            }
        }

        binding.rvHomeNewsList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = HomeNewsAdapter(createHomeNewsList()){
                    news, _ ->
                val bundle = Bundle()
                bundle.putString("name", news.title)
                bundle.putInt("image", news.image)
                Toast.makeText(requireActivity(), "Clicked on token: ${news.title}", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.newsDetailFragment, bundle)
            }
        }

        binding.rvHomeTopList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rcvAdapter

        }
    }

    private fun createHomeFavoriteList(): ArrayList<HomeFavoriteModel> {
        return arrayListOf<HomeFavoriteModel>(
            HomeFavoriteModel("BTC", "19"),
            HomeFavoriteModel("ETH", "11"),
            HomeFavoriteModel("PEJ", "11"),
            HomeFavoriteModel("RAM", "11"),
            HomeFavoriteModel("JAN", "11"),
            HomeFavoriteModel("SHI", "11")
        )
    }

    private fun createHomeNewsList(): ArrayList<HomeNewsModel> {
        return arrayListOf<HomeNewsModel>(
            HomeNewsModel(R.drawable.ic_coin, "Here comes the Coin", "This is description"),
            HomeNewsModel(R.drawable.ic_newspaper, "I am the legendary Mailbox", "This is description"),
            HomeNewsModel(R.drawable.ic_home, "Home is different to House", "This is description")
        )
    }
}