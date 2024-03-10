package com.example.instagram.profile

import com.example.instagram.common.base.BasePresenter
import com.example.instagram.common.base.BaseView
import com.example.instagram.common.model.Post
import com.example.instagram.common.model.UserAuth

interface ProfileContract {

    interface View: BaseView<Presenter>{
        fun showProgress(enabled: Boolean)
        fun displayUserProfile(userAuth: UserAuth)
        fun displayFailure(message: String)
        fun displayEmptyPosts()
        fun displaysPosts(posts: List<Post>)
    }

    interface Presenter: BasePresenter{
        var state: UserAuth?
        fun fetchUserProfile()
        fun fetchUserPosts()
    }

}