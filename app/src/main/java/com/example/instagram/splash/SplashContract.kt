package com.example.instagram.splash

import com.example.instagram.common.base.BasePresenter
import com.example.instagram.common.base.BaseView

interface SplashContract {

    interface View: BaseView<Presenter>{
        fun goToMainScreen()
        fun goToLoginScreen()
    }

    interface Presenter: BasePresenter<View>{
        fun authenticated()
    }

}