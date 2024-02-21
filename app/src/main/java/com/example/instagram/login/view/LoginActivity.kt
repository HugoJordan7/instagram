package com.example.instagram.login.view

import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.instagram.common.base.DependencyInjector
import com.example.instagram.common.util.CustomTextWatcher
import com.example.instagram.databinding.ActivityLoginBinding
import com.example.instagram.login.LoginContract
import com.example.instagram.login.data.FakeDataSource
import com.example.instagram.login.data.LoginRepository
import com.example.instagram.login.presentation.LoginPresenter
import com.example.instagram.main.view.MainActivity

class LoginActivity : AppCompatActivity(), LoginContract.View {

    private lateinit var binding: ActivityLoginBinding
    override lateinit var presenter: LoginContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = LoginPresenter(this,DependencyInjector.loginRepository())
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
        val intent = Intent(this,MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun onUserUnauthorized(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}