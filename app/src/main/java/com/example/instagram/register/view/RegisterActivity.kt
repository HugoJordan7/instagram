package com.example.instagram.register.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.instagram.R
import com.example.instagram.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val fragment = FragmentRegisterEmail()
        supportFragmentManager.beginTransaction().apply {
            add(R.id.register_fragment,fragment)
            commit()
        }
    }
}