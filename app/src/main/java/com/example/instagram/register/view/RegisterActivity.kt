package com.example.instagram.register.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.instagram.R
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
        replaceFragment(fragment)
    }

    override fun goToNameAndPasswordScreen(email: String) {
        val fragment = FragmentRegisterNamePassword()
        fragment.arguments = Bundle().apply {
            putString(KEY_EMAIL,email)
        }
        replaceFragment(fragment)
    }

    override fun goToWelcomeScreen(name: String) {
        val fragment = FragmentRegisterWelcome()
        val bundle = Bundle()
        bundle.putString(KEY_NAME,name)
        fragment.arguments = bundle
        replaceFragment(fragment)
    }

    override fun goToPhotoScreen() {
        val fragment = FragmentRegisterPhoto()
        replaceFragment(fragment)
    }

    override fun goToMainScreen() {
        val intent = Intent(this,MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            if (supportFragmentManager.findFragmentById(R.id.register_fragment) == null){
                add(R.id.register_fragment,fragment)
            } else{
                replace(R.id.register_fragment,fragment)
                addToBackStack(null)
            }
            commit()
        }
    }
}