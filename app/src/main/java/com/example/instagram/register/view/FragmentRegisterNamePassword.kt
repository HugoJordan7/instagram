package com.example.instagram.register.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.instagram.R
import com.example.instagram.databinding.FragmentRegisterNamePasswordBinding

class FragmentRegisterNamePassword: Fragment(R.layout.fragment_register_name_password) {

    companion object {
        const val KEY_EMAIL = "key_email"
    }

    private var binding: FragmentRegisterNamePasswordBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterNamePasswordBinding.bind(view)
        val email = arguments?.getString(KEY_EMAIL)
        Toast.makeText(requireContext(),email,Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

}