package com.example.instagram.feature.splash

import com.example.instagram.common.base.BasePresenter
import com.example.instagram.common.base.BaseView

interface SplashContract {

    interface View: BaseView<Presenter>{
        fun goToMainScreen()
        fun goToLoginScreen()
    }

    interface Presenter: BasePresenter{
        fun authenticated()
    }

}