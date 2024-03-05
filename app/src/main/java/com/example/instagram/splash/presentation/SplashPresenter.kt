package com.example.instagram.splash.presentation

import com.example.instagram.splash.SplashContract
import com.example.instagram.splash.data.SplashCallback
import com.example.instagram.splash.data.SplashRepository

class SplashPresenter(
    private var view: SplashContract.View?,
    private val repository: SplashRepository
) : SplashContract.Presenter {

    override fun authenticated() {
        repository.session(object : SplashCallback{
            override fun onSuccess() {
                view?.goToMainScreen()
            }
            override fun onFailure() {
                view?.goToLoginScreen()
            }
        })
    }

    override fun onDestroy() {
        view = null
    }
}