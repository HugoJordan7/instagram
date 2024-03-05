package com.example.instagram.register

import android.net.Uri
import com.example.instagram.common.base.BasePresenter
import com.example.instagram.common.base.BaseView

interface RegisterPhotoContract {

    interface View: BaseView<Presenter>{
        fun showProgress(enabled: Boolean)
        fun onUpdateSuccess()
        fun onUpdateFailure(message: String)
    }

    interface Presenter: BasePresenter{
        fun updateUser(uri: Uri)
    }

}