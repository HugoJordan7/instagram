package com.example.instagram.feature.add

import android.net.Uri
import android.os.Message
import com.example.instagram.common.base.BasePresenter
import com.example.instagram.common.base.BaseView

interface Add {

    interface Presenter: BasePresenter{
        fun createPost(uri: Uri, caption: String)
    }

    interface View: BaseView<Presenter>{
        fun showProgress(enabled: Boolean)
        fun displaySuccess()
        fun displayFailure(message: String)
    }

}