package com.example.instagram.login.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.instagram.common.util.CustomTextWatcher
import com.example.instagram.databinding.ActivityLoginBinding
import com.example.instagram.login.LoginContract

class LoginActivity : AppCompatActivity(), LoginContract.View {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding){
            loginEditEmail.addTextChangedListener(watcher)
            loginEditPassword.addTextChangedListener(watcher)
            loginBtnEnter.setOnClickListener {

            }
        }
    }

    private val watcher = CustomTextWatcher{
        binding.loginBtnEnter.isEnabled = it.isNotEmpty()
    }

    override fun showProgress(enabled: Boolean) {
        binding.loginBtnEnter.showProgress(enabled)
    }

    override fun displayEmailFailure(emailError: Int?) {
        binding.loginEditEmailLayout.error = emailError?.let { getString(it) }
    }

    override fun displayPasswordFailure(passwordError: Int?) {
        binding.loginEditPasswordLayout.error = passwordError?.let { getString(it) }
    }

    override fun onUserAuthenticated() {

    }

    override fun onUserUnauthorized() {

    }
}