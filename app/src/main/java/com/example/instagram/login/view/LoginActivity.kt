package com.example.instagram.login.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.instagram.R
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        findViewById<TextInputEditText>(R.id.login_edit_email)
            .error = "E-mail inválido"
    }
}