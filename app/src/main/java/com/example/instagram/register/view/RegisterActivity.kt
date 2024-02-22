package com.example.instagram.register.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.instagram.R
import com.example.instagram.databinding.ActivityRegisterBinding
import com.example.instagram.register.view.FragmentRegisterNamePassword.Companion.KEY_EMAIL

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