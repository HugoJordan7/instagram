package com.example.instagram.feature.search

import com.example.instagram.common.base.BasePresenter
import com.example.instagram.common.base.BaseView
import com.example.instagram.common.model.User

interface Search {

    interface View: BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayUsers(users: List<User>)
        fun displayEmptyUsers()
        fun displayFailure(message: String)
    }

    interface Presenter: BasePresenter {
        fun fetchUsers(name: String)
    }

}