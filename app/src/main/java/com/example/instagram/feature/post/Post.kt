package com.example.instagram.feature.post

import android.net.Uri
import com.example.instagram.common.base.BasePresenter
import com.example.instagram.common.base.BaseView

interface Post {

    interface View: BaseView<Presenter>{
        fun showProgress(enabled: Boolean)
        fun displayFailure(message: String)
        fun displayEmptyPictures()
        fun displayPictures(posts: List<Uri>)
    }

    interface Presenter: BasePresenter{
        fun fetchPictures()
        fun getSelectedUri(): Uri?
        fun selectUri(uri: Uri)
    }

}