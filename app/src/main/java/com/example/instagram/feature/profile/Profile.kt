package com.example.instagram.feature.profile

import com.example.instagram.common.base.BasePresenter
import com.example.instagram.common.base.BaseView
import com.example.instagram.common.model.Post
import com.example.instagram.common.model.User

interface Profile {

    interface View: BaseView<Presenter>{
        fun showProgress(enabled: Boolean)
        fun displayUserProfile(response: Pair<User, Boolean?>)
        fun displayFailure(message: String)
        fun displayEmptyPosts()
        fun displaysPosts(posts: List<Post>)
        fun followUpdated()
    }

    interface Presenter: BasePresenter{
        fun followUser(uuid: String?, follow: Boolean)
        fun fetchUserProfile(uuid: String?)
        fun fetchUserPosts(uuid: String?)
        fun clear()
    }

}