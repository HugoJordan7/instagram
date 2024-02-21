package com.example.instagram.register.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.instagram.R
import com.example.instagram.databinding.FragmentRegisterEmailBinding
import com.example.instagram.register.RegisterContract

class FragmentRegisterEmail: Fragment(R.layout.fragment_register_email), RegisterContract.View {

    private var binding: FragmentRegisterEmailBinding? = null
    override lateinit var presenter: RegisterContract.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterEmailBinding.bind(view)
    }

    override fun displayEmailFailure(emailError: Int?) {

    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}