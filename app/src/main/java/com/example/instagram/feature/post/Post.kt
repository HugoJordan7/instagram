package com.example.instagram.feature.post

import android.net.Uri
import com.example.instagram.common.base.BasePresenter
import com.example.instagram.common.base.BaseView
import com.example.instagram.common.model.Post
import com.example.instagram.common.model.UserAuth

interface Post {

    interface View: BaseView<Presenter>{
        fun showProgress(enabled: Boolean)
        fun displayFailure(message: String)
        fun displayEmptyPictures()
        fun displaysPictures(posts: List<Uri>)
    }

    interface Presenter: BasePresenter{
        fun fetchPictures()
    }

}