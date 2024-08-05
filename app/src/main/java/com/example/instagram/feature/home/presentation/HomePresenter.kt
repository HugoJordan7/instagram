package com.example.instagram.feature.home.presentation

import com.example.instagram.home.Home
import com.example.instagram.common.base.RequestCallback
import com.example.instagram.common.model.Post
import com.example.instagram.feature.home.data.HomeRepository

class HomePresenter(
    private var view: Home.View?,
    private val repository: HomeRepository
) : Home.Presenter {

    override fun fetchFeed() {
        view?.showProgress(true)
        repository.fetchFeed(object : RequestCallback<List<Post>> {
            override fun onSuccess(data: List<Post>) {
                if (data.isEmpty()) {
                    view?.displayEmptyPosts()
                } else {
                    view?.displayFullPosts(data)
                }
            }
            override fun onFailure(message: String) {
                view?.displayRequestFailure(message)
            }
            override fun onComplete() {
                view?.showProgress(false)
            }
        })
    }

    override fun clear() {
        repository.clearCache()
    }

    override fun logout() {
        repository.logout()
    }

    override fun onDestroy() {
        view = null
    }

}