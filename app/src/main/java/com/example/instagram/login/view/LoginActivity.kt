package com.example.instagram.login.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import com.example.instagram.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {

    private lateinit var enterButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val emailEditText = findViewById<TextInputEditText>(R.id.login_edit_email)
        val passwordEditText = findViewById<TextInputEditText>(R.id.login_edit_password)
        val emailInputLayout = findViewById<TextInputLayout>(R.id.login_edit_email_layout)
        val passwordInputLayout = findViewById<TextInputLayout>(R.id.login_edit_password_layout)
        enterButton = findViewById(R.id.login_btn_enter)

        emailEditText.addTextChangedListener(watcher)
        passwordEditText.addTextChangedListener(watcher)
        
        enterButton.setOnClickListener {
            emailInputLayout.error = "Esse e-mail é inválido"
            passwordInputLayout.error = "Senha incorreta"
        }
    }

    private val watcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            enterButton.isEnabled = s.toString().isNotEmpty()
        }
        override fun afterTextChanged(s: Editable?) {}
    }

}