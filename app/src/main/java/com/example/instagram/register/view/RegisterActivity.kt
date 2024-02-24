package com.example.instagram.register.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.instagram.R
import com.example.instagram.common.view.FragmentImageCropper
import com.example.instagram.common.view.FragmentImageCropper.Companion.KEY_URI
import com.example.instagram.databinding.ActivityRegisterBinding
import com.example.instagram.home.view.FragmentHome
import com.example.instagram.main.view.MainActivity
import com.example.instagram.register.view.FragmentRegisterNamePassword.Companion.KEY_EMAIL
import com.example.instagram.register.view.FragmentRegisterWelcome.Companion.KEY_NAME

class RegisterActivity : AppCompatActivity(), FragmentAttachListener {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val fragment = FragmentRegisterEmail()
        addFragment(fragment)
    }

    override fun goToNameAndPasswordScreen(email: String) {
        val fragment = FragmentRegisterNamePassword()
        fragment.arguments = Bundle().apply {
            putString(KEY_EMAIL, email)
        }
        replaceFragment(fragment)
    }

    override fun goToWelcomeScreen(name: String) {
        val fragment = FragmentRegisterWelcome()
        val bundle = Bundle()
        bundle.putString(KEY_NAME, name)
        fragment.arguments = bundle
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

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                val fragment = FragmentImageCropper().apply {
                    arguments = Bundle().apply {
                        putParcelable(KEY_URI, it)
                    }
                }
                replaceFragment(fragment)
            }
        }

    override fun goToGalleryScreen() {
        getContent?.launch("image/*")
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            add(R.id.register_fragment, fragment)
            commit()
        }
    }

    private fun replaceFragment(fragment: Fragment, toBackStack: Boolean = true) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.register_fragment, fragment)
            if (toBackStack) addToBackStack(null)
            commit()
        }
    }
}