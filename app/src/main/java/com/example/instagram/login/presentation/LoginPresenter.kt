package com.example.instagram.login.presentation

import android.util.Patterns
import com.example.instagram.R
import com.example.instagram.login.LoginContract

class LoginPresenter(override var view: LoginContract.View?): LoginContract.Presenter {

    override fun loginValidate(email: String, password: String) {
        view?.displayEmailFailure(
            if(Patterns.EMAIL_ADDRESS.matcher(email).matches()) null
            else R.string.invalid_email
        )
        view?.displayPasswordFailure(
            if(password.length >= 8) null
            else R.string.invalid_password
        )
    }

    override fun onDestroy() {
        view = null
    }

}