package com.example.instagram.login.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.instagram.common.util.CustomTextWatcher
import com.example.instagram.databinding.ActivityLoginBinding
import com.example.instagram.login.LoginContract
import com.example.instagram.login.presentation.LoginPresenter

class LoginActivity : AppCompatActivity(), LoginContract.View {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var presenter: LoginContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = LoginPresenter(this)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            loginEditEmail.addTextChangedListener(watcher)
            loginEditPassword.addTextChangedListener(watcher)
            loginBtnEnter.setOnClickListener {
                presenter.loginValidate(loginEditEmail.text.toString(), loginEditPassword.text.toString())
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