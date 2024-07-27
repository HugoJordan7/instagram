package com.example.instagram.feature.search

import com.example.instagram.common.base.BasePresenter
import com.example.instagram.common.base.BaseView
import com.example.instagram.common.model.UserAuth

interface Search {

    interface View: BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayUsers(users: List<UserAuth>)
        fun displayEmptyUsers()
        fun displayFailure(message: String)
    }

    interface Presenter: BasePresenter {
        fun fetchUsers(name: String)
    }

}