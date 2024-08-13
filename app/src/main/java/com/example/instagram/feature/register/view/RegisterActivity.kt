package com.example.instagram.feature.register.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.example.instagram.App
import com.example.instagram.R
import com.example.instagram.common.extension.replaceFragment
import com.example.instagram.common.view.FragmentImageCropper
import com.example.instagram.common.view.FragmentImageCropper.Companion.KEY_URI
import com.example.instagram.databinding.ActivityRegisterBinding
import com.example.instagram.feature.di.component.RegisterComponent
import com.example.instagram.feature.main.view.MainActivity
import com.example.instagram.feature.register.view.FragmentRegisterNamePassword.Companion.KEY_EMAIL
import com.example.instagram.feature.register.view.FragmentRegisterWelcome.Companion.KEY_NAME
import java.io.File
import java.io.IOException
import java.security.Permission
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RegisterActivity : AppCompatActivity(), FragmentAttachListener {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var currentPhoto: Uri

    lateinit var registerComponent: RegisterComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerComponent = (applicationContext as App).applicationComponent.registerComponent().create()
        registerComponent.inject(this)

        val fragment = FragmentRegisterEmail()
        replaceFragment(fragment)
    }

    override fun goToNameAndPasswordScreen(email: String) {
        val fragment = FragmentRegisterNamePassword().apply {
            arguments =  Bundle().apply {
                putString(KEY_EMAIL, email)
            }
        }
        replaceFragment(fragment)
    }

    override fun goToWelcomeScreen(name: String) {
        val fragment = FragmentRegisterWelcome().apply {
            arguments =  Bundle().apply {
                putString(KEY_NAME, name)
            }
        }
        replaceFragment(fragment)
    }

    override fun goToPhotoScreen() {
        val fragment = FragmentRegisterPhoto()
        replaceFragment(fragment)
    }

    override fun goToMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            openImageCropper(it)
        }
    }

    override fun goToGalleryScreen() {
        getContent.launch("image/*")
    }

    private val getCamera = registerForActivityResult(ActivityResultContracts.TakePicture()) { saved ->
        if(saved) {
            openImageCropper(currentPhoto)
        }
    }

    override fun goToCameraScreen() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {

            val photoFile: File? = try {
                createImageFile()
            } catch(e: IOException) {
                Log.e("RegisterActivity", e.message, e)
                null
            }

            photoFile?.also {
                val photoUri = FileProvider.getUriForFile(this, "com.example.instagram.fileprovider", it)
                currentPhoto = photoUri

                getCamera.launch(photoUri)
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timestamp}_", ".jpg", dir)
    }

    private fun replaceFragment(fragment: Fragment) {
        replaceFragment(R.id.register_fragment, fragment)
    }

    private fun openImageCropper(uri: Uri) {
        val fragment = FragmentImageCropper().apply {
            arguments = Bundle().apply {
                putParcelable(KEY_URI, uri)
            }
        }
        replaceFragment(fragment)
    }
}