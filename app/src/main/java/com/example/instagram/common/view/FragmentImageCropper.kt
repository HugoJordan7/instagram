package com.example.instagram.common.view

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.example.instagram.R
import com.example.instagram.databinding.FragmentImageCropperBinding
import com.example.instagram.feature.register.view.FragmentAttachListener
import java.io.File

class FragmentImageCropper: Fragment(R.layout.fragment_image_cropper) {

    companion object{
        const val KEY_URI = "key_uri"
    }

    private var binding: FragmentImageCropperBinding? = null
    private var attachListener: FragmentAttachListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentImageCropperBinding.bind(view)
        val uri: Uri = arguments?.getParcelable(KEY_URI) ?: throw Exception("Image uri not found!")
        binding?.apply {
            cropperContainer.setAspectRatio(1,1)
            cropperContainer.setImageUriAsync(uri)
            cropperBtnCancel.setOnClickListener {
                parentFragmentManager.popBackStack()
            }

            cropperContainer.setOnCropImageCompleteListener { view, result ->
                setFragmentResult("cropToPhoto", bundleOf(KEY_URI to result.uri))
                parentFragmentManager.popBackStack()
            }

            cropperBtnSave.setOnClickListener {
                val dir = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                if(dir != null){
                    val toSaveUri = Uri.fromFile(File(dir.path, System.currentTimeMillis().toString() + ".jpeg"))
                    cropperContainer.saveCroppedImageAsync(toSaveUri)
                }
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