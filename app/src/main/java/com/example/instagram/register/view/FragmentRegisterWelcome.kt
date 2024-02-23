package com.example.instagram.register.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.instagram.R
import com.example.instagram.databinding.FragmentRegisterWelcomeBinding

class FragmentRegisterWelcome: Fragment(R.layout.fragment_register_welcome) {

    companion object{
        const val KEY_NAME = "key_name"
    }

    private var binding: FragmentRegisterWelcomeBinding? = null
    private var attachListener: FragmentAttachListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterWelcomeBinding.bind(view)
        val name = arguments?.getString(KEY_NAME) ?: throw Exception("Name not found!")
        binding?.apply {
            registerBtnWelcomeNext.isEnabled = true
            registerTxtWelcome.text = getString(R.string.welcome_to_instagram,name)
            registerBtnWelcomeNext.setOnClickListener {
                attachListener?.goToPhotoScreen()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is FragmentAttachListener){
            attachListener = context
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}