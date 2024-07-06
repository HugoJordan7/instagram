package com.example.instagram.home

import com.example.instagram.common.base.BasePresenter
import com.example.instagram.common.base.BaseView
import com.example.instagram.common.model.Post

interface Home {

    interface Presenter: BasePresenter {
        fun fetchFeed()
    }

    interface View: BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayRequestFailure(message: String)
        fun displayEmptyPosts()
        fun displayFullPosts(posts: List<Post>)
    }

}