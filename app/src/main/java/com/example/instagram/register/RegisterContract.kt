package com.example.instagram.register

import androidx.annotation.StringRes
import com.example.instagram.common.base.BasePresenter
import com.example.instagram.common.base.BaseView

interface RegisterContract {

    interface View: BaseView<Presenter>{
        fun displayEmailFailure(@StringRes emailError: Int?)
    }

    interface Presenter: BasePresenter<View>{

    }

}