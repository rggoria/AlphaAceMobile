package com.example.alphaacemobile.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.alphaacemobile.CalculatorActivity
import com.example.alphaacemobile.R
import com.example.alphaacemobile.databinding.FragmentMoreBinding

class MoreFragment : Fragment() {

    private lateinit var binding: FragmentMoreBinding
    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMoreBinding.inflate(inflater, container, false)

        preferences = requireActivity().getSharedPreferences("shared_pref", Context.MODE_PRIVATE)

        var email = preferences.getString("email", "")
        //Toast.makeText(context, "$email", Toast.LENGTH_SHORT).show()

        binding.apply {

            if (email.isNullOrEmpty()) {
                btnMoreLogin.visibility = View.VISIBLE
                btnMoreSignup.visibility = View.VISIBLE
                editor = preferences.edit()
                rlMoreAccount.visibility = View.GONE
                btnMoreChangePass.visibility = View.GONE
                btnMoreLogout.visibility = View.GONE
            } else {
                btnMoreLogin.visibility = View.GONE
                btnMoreSignup.visibility = View.GONE
                rlMoreAccount.visibility = View.VISIBLE
                tvMoreAccountActiveEmail.text = email
                btnMoreChangePass.visibility = View.VISIBLE
                btnMoreLogout.visibility = View.VISIBLE
            }

            // Button Login
            btnMoreLogin.setOnClickListener(){
                findNavController().navigate(R.id.loginFragment)
            }

            // Button Signup
            btnMoreSignup.setOnClickListener(){
                findNavController().navigate(R.id.signupFragment)
            }

            // Button Change Password
            btnMoreChangePass.setOnClickListener(){
                findNavController().navigate(R.id.changePasswordFragment)
            }

            // Button Logout
            btnMoreLogout.setOnClickListener(){
                editor = preferences.edit()
                btnMoreLogin.visibility = View.VISIBLE
                btnMoreSignup.visibility = View.VISIBLE
                rlMoreAccount.visibility = View.GONE
                btnMoreChangePass.visibility = View.GONE
                btnMoreLogout.visibility = View.GONE
                editor.apply {
                    clear()
                    commit()
                }
                Toast.makeText(context, "Logout!!!", Toast.LENGTH_SHORT).show()
            }

            // Button Calculator Fragment
            btnMoreCalculator.setOnClickListener(){
                val intent = Intent(requireActivity(), CalculatorActivity::class.java)
                startActivity(intent)
            }

            // Button Notification Fragment
            btnMoreNotification.setOnClickListener(){
                findNavController().navigate(R.id.notificationFragment)
            }

            return root
        }
    }
}