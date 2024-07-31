package com.example.instagram.feature.register.view

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.example.instagram.R
import com.example.instagram.common.di.DependencyInjector
import com.example.instagram.common.view.CustomDialog
import com.example.instagram.common.view.FragmentImageCropper.Companion.KEY_URI
import com.example.instagram.databinding.FragmentRegisterPhotoBinding
import com.example.instagram.feature.register.RegisterPhotoContract
import com.example.instagram.feature.register.presentation.RegisterPhotoPresenter

class FragmentRegisterPhoto: Fragment(R.layout.fragment_register_photo), RegisterPhotoContract.View {

    private var binding: FragmentRegisterPhotoBinding? = null
    private var attachListener: FragmentAttachListener? = null
    override lateinit var presenter: RegisterPhotoContract.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterPhotoBinding.bind(view)
        val repository = DependencyInjector.registerRepository()
        presenter = RegisterPhotoPresenter(this,repository)

        setFragmentResultListener("cropToPhoto"){ requestKey, bundle ->
            val profileImageUri: Uri? = bundle.getParcelable(KEY_URI)
            profileImageUri?.let {
                binding?.registerImgProfile?.setImageURI(it)
                presenter.updateUser(it)
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

    override fun showProgress(enabled: Boolean) {
        binding?.registerBtnPhotoNext?.showProgress(enabled)
    }

    override fun onUpdateSuccess() {
        attachListener?.goToMainScreen()
    }

    override fun onUpdateFailure(message: String) {
        Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()
    }

    private fun displayCustomDialog(){
        val customDialog = CustomDialog(requireContext())
        customDialog.addButton(R.string.photo,R.string.gallery){
            when(it.id){
                R.string.photo -> {
                    attachListener?.goToCameraScreen()
                }
                R.string.gallery -> {
                    attachListener?.goToGalleryScreen()
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
        presenter.onDestroy()
        super.onDestroy()
    }

}