package com.example.alphaacemobile.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.android.volley.Response
import com.android.volley.RetryPolicy
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.alphaacemobile.Constant
import com.example.alphaacemobile.R
import com.example.alphaacemobile.databinding.FragmentSignupBinding
import org.json.JSONObject

class SignupFragment : Fragment() {

    private lateinit var binding: FragmentSignupBinding
    private lateinit var preferences: SharedPreferences
    private lateinit var editor : SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignupBinding.inflate(layoutInflater, container, false)

        //SharedPreference
        preferences = requireActivity().getSharedPreferences("shared_pref", Context.MODE_PRIVATE)
        editor = preferences.edit()

        binding.apply {
            // TextLayout Signup Email
            tietSignupEmail.doOnTextChanged { text, start, before, count ->
                if (text!!.isEmpty()) {
                    tilSignupEmail.error = "Required"
                } else {
                    tilSignupEmail.error = null
                }
            }

            // TextLayout Signup Password
            tietSignupPassword.doOnTextChanged { text, start, before, count ->
                if (text!!.isEmpty()) {
                    tilSignupPassword.error = "Required"
                } else {
                    tilSignupPassword.error = null
                }
            }

            // Button Signup Confirm
            btnSignupConfirm.setOnClickListener {
                if (tietSignupEmail.text.toString().isEmpty()) {
                    Toast.makeText(context, "Email is required", Toast.LENGTH_SHORT).show()
                } else if (tietSignupPassword.text.toString().isEmpty()) {
                    Toast.makeText(context, "Password is required", Toast.LENGTH_SHORT).show()
                } else {
                    val email = tietSignupEmail.text.toString()
                    val password = tietSignupPassword.text.toString()
                    addData(email, password)
                }
            }

            // Button Login
            btnSignupLogin.setOnClickListener {
                requireActivity().onBackPressed()
                findNavController().navigate(R.id.loginFragment)
            }

            return root
        }
    }

    private fun addData(email: String, password: String) {
        val url = Constant.BASE_URL + "insert_signup_data"
        val requestQueue = Volley.newRequestQueue(requireActivity())
        val stringRequest = object : StringRequest(
            Method.POST,url,
            Response.Listener {
                    response ->
                val jsonObject = JSONObject(response)

                // Debugging purposes
                Log.d("SIGNUP_ACTIVITY", response)

                if(jsonObject.get("response").equals("Successful")) {
                    editor.apply {
                        putString("email", email)
                        putString("password", password)
                        commit()
                    }
                    Toast.makeText(context, "Account Created!!!", Toast.LENGTH_SHORT).show()
                    requireActivity().onBackPressed()
                }
                else {
                    Toast.makeText(requireActivity(),"Request error", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener {
                    error ->
                Toast.makeText(requireActivity(),error.toString(), Toast.LENGTH_SHORT).show()
            })
        {
            override fun getParams(): HashMap<String,String>{
                val map = HashMap<String,String>()
                map["email"] = email
                map["password"] = password
                map["request"] = "Sent"
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