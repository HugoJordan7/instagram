package com.example.instagram.register.view

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.example.instagram.R
import com.example.instagram.common.view.CustomDialog
import com.example.instagram.common.view.FragmentImageCropper.Companion.KEY_URI
import com.example.instagram.databinding.FragmentRegisterPhotoBinding

class FragmentRegisterPhoto: Fragment(R.layout.fragment_register_photo) {

    private var binding: FragmentRegisterPhotoBinding? = null
    private var attachListener: FragmentAttachListener? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterPhotoBinding.bind(view)
        setFragmentResultListener("cropToPhoto"){ requestKey, bundle ->
            val profileImageUri: Uri? = bundle.getParcelable(KEY_URI)
            profileImageUri?.let {
                binding?.registerImgProfile?.setImageURI(it)
            }
        }
        binding?.apply {
            registerBtnPhotoNext.isEnabled = true
            registerBtnPhotoNext.setOnClickListener {
                displayCustomDialog()
            }
            registerBtnJump.setOnClickListener {
                attachListener?.goToMainScreen()
            }
        }
    }

    private fun displayCustomDialog(){
        val customDialog = CustomDialog(requireContext())
        customDialog.addButton(R.string.gallery,R.string.photo){
            when(it.id){
                R.string.gallery -> {
                    attachListener?.goToGalleryScreen()
                }
                R.string.photo -> {
                    Log.i("dialogTest","photo")
                }
            }
        }
        customDialog.show()
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