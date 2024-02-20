package com.example.instagram.login

interface LoginContract {

    interface View{
        fun showProgress(enabled: Boolean)
        fun displayEmailFailure(emailError: Int?)
        fun displayPasswordFailure(passwordError: Int?)
        fun onUserAuthenticated()
        fun onUserUnauthorized()
    }

    interface Presenter{
        fun loginValidate(email: String, password: String)
    }

}