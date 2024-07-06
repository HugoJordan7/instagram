package com.example.instagram.feature.register.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.instagram.R
import com.example.instagram.common.extension.replaceFragment
import com.example.instagram.common.view.FragmentImageCropper
import com.example.instagram.common.view.FragmentImageCropper.Companion.KEY_URI
import com.example.instagram.databinding.ActivityRegisterBinding
import com.example.instagram.feature.main.view.MainActivity
import com.example.instagram.feature.register.view.FragmentRegisterNamePassword.Companion.KEY_EMAIL
import com.example.instagram.feature.register.view.FragmentRegisterWelcome.Companion.KEY_NAME
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.jvm.Throws

class RegisterActivity : AppCompatActivity(), FragmentAttachListener {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var currentPhotoUri: Uri
    private val fragmentId: Int = R.id.register_fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val fragment = FragmentRegisterEmail()
        replaceFragment(fragmentId,fragment)
    }

    override fun goToNameAndPasswordScreen(email: String) {
        val fragment = FragmentRegisterNamePassword()
        fragment.arguments = Bundle().apply {
            putString(KEY_EMAIL, email)
        }
        replaceFragment(fragmentId,fragment)
    }

    override fun goToWelcomeScreen(name: String) {
        val fragment = FragmentRegisterWelcome()
        val bundle = Bundle()
        bundle.putString(KEY_NAME, name)
        fragment.arguments = bundle
        replaceFragment(fragmentId,fragment)
    }

    override fun goToPhotoScreen() {
        val fragment = FragmentRegisterPhoto()
        replaceFragment(fragmentId,fragment)
    }

    override fun goToMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                openImageCropper(it)
            }
        }

    private val getCamera = registerForActivityResult(ActivityResultContracts.TakePicture()){ saved ->
        if(saved){
            openImageCropper(currentPhotoUri)
        }
    }

    override fun goToGalleryScreen() {
        getContent.launch("image/*")
    }

    override fun goToCameraScreen() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if(intent.resolveActivity(packageManager) != null){
            val photoFile: File? = try{
                createImageFile()
            } catch (e: IOException){
                null
            }
            photoFile?.let {
                val photoUri = FileProvider.getUriForFile(this,"com.example.instagram.fileprovider",it)
                currentPhotoUri = photoUri
                getCamera.launch(photoUri)
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timestamp}_",".jpeg",dir)
    }

    private fun openImageCropper(uri: Uri){
        val fragment = FragmentImageCropper().apply {
            arguments = Bundle().apply {
                putParcelable(KEY_URI, uri)
            }
        }
        replaceFragment(fragmentId,fragment)
    }
}