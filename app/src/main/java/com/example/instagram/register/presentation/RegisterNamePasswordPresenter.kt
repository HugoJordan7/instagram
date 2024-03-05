package com.example.instagram.register.presentation

import com.example.instagram.R
import com.example.instagram.register.RegisterNamePasswordContract
import com.example.instagram.register.data.RegisterCallback
import com.example.instagram.register.data.RegisterRepository

class RegisterNamePasswordPresenter(
    private var view: RegisterNamePasswordContract.View?,
    private val repository: RegisterRepository
) : RegisterNamePasswordContract.Presenter {

    override fun register(email: String, name: String, password: String, confirm: String) {
        val isNameValid = name.length >= 4
        val isPasswordValid = password.length >= 8
        val isConfirmValid = password == confirm
        view?.apply {
            displayNameFailure(if (isNameValid) null else R.string.invalid_name)
            displayPasswordFailure(if (isPasswordValid) null else R.string.invalid_password)
            if(isPasswordValid){
                displayPasswordFailure(if (isConfirmValid) null else R.string.password_not_equal)
            }
        }

        if(isNameValid && isPasswordValid && isConfirmValid){
            view?.showProgress(true)
            repository.register(email,name,password, object : RegisterCallback{
                override fun onSuccess() {
                    view?.onRegisterSuccess(name)
                }
                override fun onFailure(message: String) {
                    view?.onRegisterFailure(message)
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