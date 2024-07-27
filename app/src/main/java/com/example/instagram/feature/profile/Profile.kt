package com.example.instagram.feature.profile

import com.example.instagram.common.base.BasePresenter
import com.example.instagram.common.base.BaseView
import com.example.instagram.common.model.Post
import com.example.instagram.common.model.UserAuth

interface Profile {

    interface View: BaseView<Presenter>{
        fun showProgress(enabled: Boolean)
        fun displayUserProfile(userAuth: Pair<UserAuth, Boolean?>)
        fun displayFailure(message: String)
        fun displayEmptyPosts()
        fun displaysPosts(posts: List<Post>)
    }

    interface Presenter: BasePresenter{
        fun fetchUserProfile(uuid: String?)
        fun fetchUserPosts(uuid: String?)
        fun clear()
    }

}