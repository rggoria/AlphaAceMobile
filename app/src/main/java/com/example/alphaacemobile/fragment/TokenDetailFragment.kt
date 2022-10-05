package com.example.alphaacemobile.fragment

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.RetryPolicy
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.alphaacemobile.Constant
import com.example.alphaacemobile.R
import com.example.alphaacemobile.databinding.FragmentTokenDetailBinding
import com.example.alphaacemobile.model.HomeTopModel
import org.json.JSONObject
import java.util.concurrent.Executors

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
            getData(tokenName)
            return root
        }
    }

    // Token Data
    private fun getData(tokenName: String?) {
        val url = Constant.BASE_URL + "get_token_detail"
        val requestQueue = Volley.newRequestQueue(requireActivity())

        val stringRequest = object : StringRequest(
            Method.POST,url,
            Response.Listener
            { response ->
                // Debugging purposes
                Log.d("Token_Detail_Fragment", response)
                val jsonObject = JSONObject(response)

                if(jsonObject.get("response").equals("Successful")) {
                    val jsonArray = jsonObject.getJSONArray("data")

                    for (i in 0..jsonArray.length()-1) {
                        val jo = jsonArray.getJSONObject(i)
                        //store your variable
                        val top = jo.getString("top").toString()
                        val token_name = jo.getString("token_name").toString()
                        val curr_prc = jo.getString("curr_prc").toString()
                        val cir_supp = jo.getString("cir_supp").toString()
                        val total_vol = jo.getString("total_vol").toString()
                        val token_img = jo.getString("token_img").toString()
                        val price_24h = jo.getString("price_24h").toDouble()
                        val symbol = jo.getString("symbol").toString().uppercase()
                        val reddit_link = jo.getString("reddit_link").toString()
                        val facebook_link = jo.getString("facebook_link").toString()
                        val linkedin = jo.getString("linkedin").toString()
                        val discord_invite = jo.getString("discord_invite").toString()
                        val twitter = jo.getString("twitter").toString()
                        val telegram = jo.getString("telegram").toString()
                        val coin_link = jo.getString("coin_link").toString()
                        val website = jo.getString("website").toString()

                        Log.d("top", top)
                        Log.d("tk_name", token_name)
                        Log.d("tk_price", curr_prc)
                        Log.d("cir_supp", cir_supp)
                        Log.d("total_vol", total_vol)
                        Log.d("token_img", token_img)
                        Log.d("price_24h", price_24h.toString())
                        Log.d("reddit_link", reddit_link)
                        Log.d("facebook_link", facebook_link)
                        Log.d("linkedin", linkedin)
                        Log.d("discord_invite", discord_invite)
                        Log.d("twitter", twitter)
                        Log.d("telegram", telegram)
                        Log.d("coin_link", coin_link)
                        Log.d("website", website)


                        // Price
                        val vSymbol = binding.tvTokenDetailSymbol
                        val vPrice = binding.tvTokenDetailPriceValue
                        val vCirculatingSup = binding.tvTokenDetailCirculatingSupValue
                        val vTotalVol = binding.tvTokenDetailVolumeValue
                        val vMarketCap24 = binding.tvTokenDetailMarketCap24Value

                        // Imageview
                        // Declaring executor to parse the URL
                        val executor = Executors.newSingleThreadExecutor()

                        // Once the executor parses the URL
                        // and receives the image, handler will load it
                        // in the ImageView
                        val handler = Handler(Looper.getMainLooper())

                        // Initializing the image
                        var image: Bitmap? = null

                        // Only for Background process (can take time depending on the Internet speed)
                        executor.execute {

                            // Image URL
                            val imageURL = token_img

                            // Tries to get the image and post it in the ImageView
                            // with the help of Handler
                            try {
                                val `in` = java.net.URL(imageURL).openStream()
                                image = BitmapFactory.decodeStream(`in`)

                                // Only for making changes in UI
                                handler.post {
                                    binding.ivTokenDetail.setImageBitmap(image)
                                }
                            }

                            // If the URL doesnot point to
                            // image or any other kind of failure
                            catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }

                        // Setting up data
                        vSymbol.text = "$symbol"
                        vPrice.text = "$curr_prc"
                        vCirculatingSup.text = "$cir_supp"
                        vTotalVol.text = "$total_vol"
                        vMarketCap24.text = "$price_24h"
                        //Market Price Coloring Coloring
                        if (price_24h > 0) {
                            vMarketCap24.setTextColor(Color.parseColor("#00FF00"))
                        } else {
                            vMarketCap24.setTextColor(Color.parseColor("#FF0000"))
                        }

                        // Showing and Hiding Icon Links
                        if (reddit_link == "null"){ // Reddit
                            binding.imTokenDetailSocialReddit.visibility =View.GONE
                        } else {
                            binding.imTokenDetailSocialReddit.visibility =View.VISIBLE
                            binding.imTokenDetailSocialReddit.setOnClickListener {
                                Toast.makeText(requireActivity(),"Reddit",Toast.LENGTH_SHORT).show()
                                val urlIntent = Intent(Intent.ACTION_VIEW, Uri.parse(reddit_link))
                                startActivity(urlIntent)
                            }
                        }
                        if (facebook_link == "null"){ // Facebook
                            binding.imTokenDetailSocialFacebook.visibility =View.GONE
                        } else {
                            binding.imTokenDetailSocialFacebook.visibility =View.VISIBLE
                            binding.imTokenDetailSocialFacebook.setOnClickListener {
                                Toast.makeText(requireActivity(),"Facebook",Toast.LENGTH_SHORT).show()
                                val urlIntent = Intent(Intent.ACTION_VIEW, Uri.parse(facebook_link))
                                startActivity(urlIntent)
                            }
                        }
                        if (linkedin == "null"){ // Linkedin
                            binding.imTokenDetailSocialLinkedin.visibility =View.GONE
                        } else {
                            binding.imTokenDetailSocialLinkedin.visibility =View.VISIBLE
                            binding.imTokenDetailSocialLinkedin.setOnClickListener {
                                Toast.makeText(requireActivity(),"Linkedin",Toast.LENGTH_SHORT).show()
                                val urlIntent = Intent(Intent.ACTION_VIEW, Uri.parse(linkedin))
                                startActivity(urlIntent)
                            }
                        }
                        if (discord_invite == "null"){ // Discord
                            binding.imTokenDetailSocialDiscord.visibility =View.GONE
                        } else {
                            binding.imTokenDetailSocialDiscord.visibility =View.VISIBLE
                            binding.imTokenDetailSocialDiscord.setOnClickListener {
                                Toast.makeText(requireActivity(),"Discord",Toast.LENGTH_SHORT).show()
                                val urlIntent = Intent(Intent.ACTION_VIEW, Uri.parse(discord_invite))
                                startActivity(urlIntent)
                            }
                        }
                        if (twitter == "null"){ // Twitter
                            binding.imTokenDetailSocialTwitter.visibility =View.GONE
                        } else {
                            binding.imTokenDetailSocialTwitter.visibility =View.VISIBLE
                            binding.imTokenDetailSocialTwitter.setOnClickListener {
                                Toast.makeText(requireActivity(),"Twitter",Toast.LENGTH_SHORT).show()
//                                val urlIntent = Intent(Intent.ACTION_VIEW, Uri.parse(twitter))
//                                startActivity(urlIntent)
                            }
                        }
                        if (telegram == "null"){ // Telegram
                            binding.imTokenDetailSocialTelegram.visibility =View.GONE
                        } else {
                            binding.imTokenDetailSocialTelegram.visibility =View.VISIBLE
                            binding.imTokenDetailSocialTelegram.setOnClickListener {
                                Toast.makeText(requireActivity(),"Telegram",Toast.LENGTH_SHORT).show()
                                val urlIntent = Intent(Intent.ACTION_VIEW, Uri.parse(telegram))
                                startActivity(urlIntent)
                            }
                        }
                        if (coin_link == "null"){ // Coingecko
                            binding.imTokenDetailSocialCoingecko.visibility =View.GONE
                        } else {
                            binding.imTokenDetailSocialCoingecko.visibility =View.VISIBLE
                            binding.imTokenDetailSocialCoingecko.setOnClickListener {
                                Toast.makeText(requireActivity(),"Coingecko",Toast.LENGTH_SHORT).show()
                                val urlIntent = Intent(Intent.ACTION_VIEW, Uri.parse(coin_link))
                                startActivity(urlIntent)
                            }
                        }
                        if (website == "null"){ // Website
                            binding.imTokenDetailSocialWebsite.visibility =View.GONE
                        } else {
                            binding.imTokenDetailSocialWebsite.visibility =View.VISIBLE
                            binding.imTokenDetailSocialWebsite.setOnClickListener {
                                Toast.makeText(requireActivity(),"Website",Toast.LENGTH_SHORT).show()
                                val urlIntent = Intent(Intent.ACTION_VIEW, Uri.parse(website.trim()))
                                startActivity(urlIntent)
                            }
                        }
                    }

                }
                else if(jsonObject.get("response").equals("Failed")){
                    Toast.makeText(requireActivity(), "Failed to Load Token Details", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Toast.makeText(requireActivity(),"Request error",Toast.LENGTH_SHORT).show()
                }

            }, Response.ErrorListener
            {
                    error ->
                Toast.makeText(requireActivity(),error.toString(),Toast.LENGTH_SHORT).show()
            })
        {
            override fun getParams(): HashMap<String, String>
            {
                val map = HashMap<String,String>()
                map["request"] = "Sent"
                map["name"] = "$tokenName"
                return map
            }
        }

        requestQueue.add(stringRequest)

        stringRequest.retryPolicy = object : RetryPolicy {
            override fun getCurrentTimeout(): Int {
                return 20000
            }

            override fun getCurrentRetryCount(): Int {
                return 20000
            }

            override fun retry(error: VolleyError?) {
                return retry(error)
            }
        }
    }

}