package com.example.instagram.feature.register.presentation

import android.util.Patterns
import com.example.instagram.R
import com.example.instagram.common.base.RequestCallback
import com.example.instagram.feature.register.RegisterEmailContract
import com.example.instagram.feature.register.data.RegisterRepository

class RegisterEmailPresenter(
    private var view: RegisterEmailContract.View?,
    private val repository: RegisterRepository
) : RegisterEmailContract.Presenter {

    override fun register(email: String) {
        val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        view?.displayEmailFailure(if (isEmailValid) null else R.string.invalid_email)
        if(isEmailValid){
            view?.showProgress(true)
            repository.register(email, object : RequestCallback<Boolean> {
                override fun onSuccess(data: Boolean) {
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