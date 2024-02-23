package com.example.instagram.register.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.instagram.R
import com.example.instagram.common.view.CustomDialog
import com.example.instagram.databinding.FragmentRegisterPhotoBinding

class FragmentRegisterPhoto: Fragment(R.layout.fragment_register_photo) {

    private var binding: FragmentRegisterPhotoBinding? = null
    private var attachListener: FragmentAttachListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterPhotoBinding.bind(view)

        binding?.apply {
            registerBtnPhotoNext.isEnabled = true
            registerBtnPhotoNext.setOnClickListener {
                val customDialog = CustomDialog(requireContext())
                customDialog.addButton(R.string.gallery,R.string.photo){
                    when(it.id){
                        R.string.gallery -> {
                            Log.i("dialogTest","gallery")
                        }
                        R.string.photo -> {
                            Log.i("dialogTest","photo")
                        }
                    }
                }
                customDialog.show()
            }
            registerBtnJump.setOnClickListener {
                attachListener?.goToMainScreen()
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
        binding = null
        super.onDestroy()
    }

}