package com.example.instagram.profile

import com.example.instagram.common.base.BasePresenter
import com.example.instagram.common.base.BaseView

interface ProfileContract {

    interface View: BaseView<Presenter>{
        fun showProgress(enabled: Boolean)
    }

    interface Presenter: BasePresenter{
        fun fetchUserProfile()
        fun fetchUserPosts()
    }

}