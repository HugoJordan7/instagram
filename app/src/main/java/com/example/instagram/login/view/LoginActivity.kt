package com.example.instagram.login.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import com.example.instagram.R
import com.example.instagram.databinding.ActivityLoginBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding){
            loginEditEmail.addTextChangedListener(watcher)
            loginEditPassword.addTextChangedListener(watcher)
            loginBtnEnter.setOnClickListener {
                loginBtnEnter.showProgress(true)
                Handler(Looper.getMainLooper()).postDelayed({
                    loginBtnEnter.showProgress(false)
                }, 2000)
                loginEditEmailLayout.error = "Esse e-mail é inválido"
                loginEditPasswordLayout.error = "Senha incorreta"
            }
        }
    }

    private val watcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            binding.loginBtnEnter.isEnabled = s.toString().isNotEmpty()
        }
        override fun afterTextChanged(s: Editable?) {}
    }

}