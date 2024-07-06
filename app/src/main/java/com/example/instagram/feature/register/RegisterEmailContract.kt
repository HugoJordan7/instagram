package com.example.instagram.feature.register

import androidx.annotation.StringRes
import com.example.instagram.common.base.BasePresenter
import com.example.instagram.common.base.BaseView

interface RegisterEmailContract {

    interface View: BaseView<Presenter>{
        fun showProgress(enabled: Boolean)
        fun displayEmailFailure(@StringRes emailError: Int?)
        fun onEmailFailure(message: String)
        fun goToNameAndPasswordScreen(email: String)
    }

    interface Presenter: BasePresenter{
        fun register(email: String)
    }

}