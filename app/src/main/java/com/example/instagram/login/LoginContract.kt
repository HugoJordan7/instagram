package com.example.instagram.login

import com.example.instagram.common.base.BasePresenter
import com.example.instagram.common.base.BaseView

interface LoginContract {

    interface View: BaseView<Presenter>{
        fun showProgress(enabled: Boolean)
        fun displayEmailFailure(emailError: Int?)
        fun displayPasswordFailure(passwordError: Int?)
        fun onUserAuthenticated()
        fun onUserUnauthorized(message: String)
    }

    interface Presenter: BasePresenter<View>{
        fun loginValidate(email: String, password: String)
    }

}