package com.example.instagram.feature.login.presentation

import android.util.Patterns
import com.example.instagram.R
import com.example.instagram.common.base.RequestCallback
import com.example.instagram.feature.login.LoginContract
import com.example.instagram.feature.login.data.LoginRepository

class LoginPresenter(
    private var view: LoginContract.View?,
    private val repository: LoginRepository
) : LoginContract.Presenter {

    override fun loginValidate(email: String, password: String) {
        val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val isPasswordValid = password.length >= 8
        view?.displayEmailFailure(if (isEmailValid) null else R.string.invalid_email)
        view?.displayPasswordFailure(if (isPasswordValid) null else R.string.invalid_password)

        if(isEmailValid && isPasswordValid){
            view?.showProgress(true)
            repository.login(email,password, object : RequestCallback<Boolean> {
                override fun onSuccess(data: Boolean) {
                    view?.onUserAuthenticated()
                }
                override fun onFailure(message: String) {
                    view?.onUserUnauthorized(message)
                }
                override fun onComplete() {
                    view?.showProgress(false)
                }
            })
        }
    }

    override fun onDestroy() {
        view = null
    }

}