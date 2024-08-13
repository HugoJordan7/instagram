package com.example.instagram.feature.login.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.instagram.common.di.DependencyInjector
import com.example.instagram.common.util.CustomTextWatcher
import com.example.instagram.databinding.ActivityLoginBinding
import com.example.instagram.feature.login.presentation.LoginViewModel
import com.example.instagram.feature.main.view.MainActivity
import com.example.instagram.feature.register.view.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.isEmailFailure.observe(this){ message ->
            message?.let { displayEmailFailure(it) }
        }

        viewModel.isPasswordFailure.observe(this){ message ->
            message?.let { displayPasswordFailure(it) }
        }

        viewModel.isUserUnauthorized.observe(this){ message ->
            message?.let { onUserUnauthorized(it) }
        }

        viewModel.isLoading.observe(this){ isLoading ->
            showProgress(isLoading)
        }

        viewModel.isAuthenticated.observe(this){ isAuthenticated ->
            if (isAuthenticated) onUserAuthenticated()
        }

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            loginEditEmail.addTextChangedListener(watcher)
            loginEditPassword.addTextChangedListener(watcher)
            loginBtnEnter.setOnClickListener {
                viewModel.loginValidate(loginEditEmail.text.toString(), loginEditPassword.text.toString())
            }
            loginTxtRegister.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }
        }
    }

    private val watcher = CustomTextWatcher { currentEditTextString ->
        val email = binding.loginEditEmail.text.toString()
        val password = binding.loginEditPassword.text.toString()
        binding.loginBtnEnter.isEnabled = email.isNotEmpty() && password.isNotEmpty()
        if(currentEditTextString == email){
            displayEmailFailure(null)
        } else{
            displayPasswordFailure(null)
        }
    }

    private fun showProgress(enabled: Boolean) {
        binding.loginBtnEnter.showProgress(enabled)
    }

    private fun displayEmailFailure(emailError: Int?) {
        binding.loginEditEmailLayout.error = emailError?.let { getString(it) }
    }

    private fun displayPasswordFailure(passwordError: Int?) {
        binding.loginEditPasswordLayout.error = passwordError?.let { getString(it) }
    }

    private fun onUserAuthenticated() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun onUserUnauthorized(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }

}