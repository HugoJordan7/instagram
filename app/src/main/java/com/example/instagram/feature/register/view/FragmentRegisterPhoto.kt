package com.example.instagram.feature.register.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.example.instagram.R
import com.example.instagram.common.base.BaseFragmentMVVM
import com.example.instagram.common.di.DependencyInjector
import com.example.instagram.common.view.CustomDialog
import com.example.instagram.common.view.FragmentImageCropper.Companion.KEY_URI
import com.example.instagram.databinding.FragmentRegisterNamePasswordBinding
import com.example.instagram.databinding.FragmentRegisterPhotoBinding
import com.example.instagram.feature.register.RegisterPhotoContract
import com.example.instagram.feature.register.presentation.RegisterPhotoViewModel

class FragmentRegisterPhoto: BaseFragmentMVVM<FragmentRegisterPhotoBinding, RegisterPhotoViewModel>(
    R.layout.fragment_register_photo,
    FragmentRegisterPhotoBinding::bind
) {

    override lateinit var viewModel: RegisterPhotoViewModel

    private var attachListener: FragmentAttachListener? = null

    override fun setupViews() {
        setFragmentResultListener("cropToPhoto"){ requestKey, bundle ->
            val profileImageUri: Uri? = bundle.getParcelable(KEY_URI)
            profileImageUri?.let {
                binding?.registerImgProfile?.setImageURI(it)
                viewModel.updateUser(it)
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
            when(resources.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)){
                Configuration.UI_MODE_NIGHT_YES ->{
                    registerImgProfile.imageTintList = ColorStateList.valueOf(Color.WHITE)
                }
                Configuration.UI_MODE_NIGHT_NO ->{
                    registerImgProfile.imageTintList = ColorStateList.valueOf(Color.BLACK)
                }
            }
        }

        viewModel.isLoading.observe(this){ isLoading ->
            showProgress(isLoading)
        }

        viewModel.isFailure.observe(this){ message ->
            message?.let { onUpdateFailure(it) }
        }

        viewModel.isSuccess.observe(this){ isSuccess ->
            if (isSuccess) onUpdateSuccess()
        }

    }

    private fun showProgress(enabled: Boolean) {
        binding?.registerBtnPhotoNext?.showProgress(enabled)
    }

    private fun onUpdateSuccess() {
        attachListener?.goToMainScreen()
    }

    private fun onUpdateFailure(message: String) {
        Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()
    }

    private fun displayCustomDialog(){
        val customDialog = CustomDialog(requireContext())
        customDialog.addButton(R.string.photo,R.string.gallery){
            when(it.id){
                R.string.photo -> {
                    if (allPermissionsGranted()) {
                        attachListener?.goToCameraScreen()
                    } else {
                        getPermission.launch(REQUIRED_PERMISSION)
                    }
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


    private val getPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (allPermissionsGranted()) {
                attachListener?.goToCameraScreen()
            } else {
                Toast.makeText(requireContext(), R.string.permission_camera_denied, Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted(): Boolean =
        ContextCompat.checkSelfPermission(requireContext(), REQUIRED_PERMISSION) == PackageManager.PERMISSION_GRANTED

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }

}