package com.example.instagram.register.presentation

import android.util.Patterns
import com.example.instagram.R
import com.example.instagram.register.RegisterEmailContract
import com.example.instagram.register.data.RegisterEmailCallback
import com.example.instagram.register.data.RegisterEmailRepository

class RegisterEmailPresenter(
    override var view: RegisterEmailContract.View?,
    private val repository: RegisterEmailRepository) : RegisterEmailContract.Presenter {

    override fun register(email: String) {
        val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        view?.displayEmailFailure(if (isEmailValid) null else R.string.invalid_email)
        if(isEmailValid){
            view?.showProgress(true)
            repository.register(email, object : RegisterEmailCallback{
                override fun onSuccess() {
                    view?.goToNameAndPasswordScreen(email)
                }

                override fun onFailure(message: String) {
                    view?.onEmailFailure(message)
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