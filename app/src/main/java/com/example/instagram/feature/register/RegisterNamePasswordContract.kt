package com.example.instagram.feature.register

import androidx.annotation.StringRes
import com.example.instagram.common.base.BasePresenter
import com.example.instagram.common.base.BaseView

interface RegisterNamePasswordContract {

    interface View: BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayNameFailure(@StringRes nameError: Int?)
        fun displayPasswordFailure(@StringRes passwordError: Int?)
        fun onRegisterFailure(message: String)
        fun onRegisterSuccess(name: String)
    }

    interface Presenter: BasePresenter {
        fun register(email: String, name: String, password: String, confirm: String)
    }

}