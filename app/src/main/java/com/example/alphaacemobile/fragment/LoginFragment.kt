package com.example.alphaacemobile.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.android.volley.Response
import com.android.volley.RetryPolicy
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.alphaacemobile.Constant.BASE_URL
import com.example.alphaacemobile.R
import com.example.alphaacemobile.databinding.FragmentLoginBinding
import org.json.JSONObject

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var preferences: SharedPreferences
    private lateinit var editor : SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        // SharedPreference
        preferences = requireActivity().getSharedPreferences("shared_pref", Context.MODE_PRIVATE)

        editor = preferences.edit()

        binding.apply {

            // Button Login Confirm
            btnLoginConfirm.setOnClickListener {
                val email = tietLoginEmail.text.toString()
                val password = tietLoginPassword.text.toString()
                getData(email, password)
            }

            // TextView Forget Password
            tvLoginForget.setOnClickListener() {
                requireActivity().onBackPressed()
                findNavController().navigate(R.id.forgetPasswordFragment)
            }

            // TextView Signup
            btnLoginSignup.setOnClickListener {
                requireActivity().onBackPressed()

            }

            return root
        }
    }

    private fun getData(email: String, password: String) {
        val url = BASE_URL + "get_login_data"
        val requestQueue = Volley.newRequestQueue(requireActivity())

        val stringRequest = object : StringRequest(
            Method.POST,url,
            Response.Listener
            { response ->
                // Debugging purposes
                Log.d("Login_Fragment", response)
                val jsonObject = JSONObject(response)

                if(jsonObject.get("response").equals("Approved"))
                {
                    editor.apply {
                        putString("email", email)
                        putString("password", password)
                        commit()
                    }
                    requireActivity().onBackPressed()


                }
                else if(jsonObject.get("response").equals("Failed"))
                {
                    Toast.makeText(requireActivity(), "Account ID/Password is Incorrect", Toast.LENGTH_SHORT).show()
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
                map["email"] = email
                map["password"] = password
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