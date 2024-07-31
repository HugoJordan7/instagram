package com.example.instagram.feature.splash.presentation

import com.example.instagram.common.base.RequestCallback
import com.example.instagram.feature.splash.SplashContract
import com.example.instagram.feature.splash.data.SplashRepository

class SplashPresenter(
    private var view: SplashContract.View?,
    private val repository: SplashRepository
) : SplashContract.Presenter {

    override fun authenticated() {
        repository.session(object : RequestCallback<Boolean> {
            override fun onSuccess(data: Boolean) {
                view?.goToMainScreen()
            }
            override fun onFailure(message: String) {
                view?.goToLoginScreen()
            }
            override fun onComplete() {}
        })
    }

    override fun onDestroy() {
        view = null
    }
}