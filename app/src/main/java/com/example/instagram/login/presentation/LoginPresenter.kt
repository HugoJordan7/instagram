package com.example.instagram.login.presentation

import android.util.Patterns
import com.example.instagram.R
import com.example.instagram.common.model.UserAuth
import com.example.instagram.login.LoginContract
import com.example.instagram.login.data.LoginCallback
import com.example.instagram.login.data.LoginRepository

class LoginPresenter(
    override var view: LoginContract.View?,
    private val repository: LoginRepository
) : LoginContract.Presenter {

    override fun loginValidate(email: String, password: String) {
        val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val isPasswordValid = password.length >= 8
        view?.displayEmailFailure(if (isEmailValid) null else R.string.invalid_email)
        view?.displayPasswordFailure(if (isPasswordValid) null else R.string.invalid_password)

        if(isEmailValid && isPasswordValid){
            view?.showProgress(true)
            repository.login(email,password, object : LoginCallback{
                override fun onSuccess(userAuth: UserAuth) {
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