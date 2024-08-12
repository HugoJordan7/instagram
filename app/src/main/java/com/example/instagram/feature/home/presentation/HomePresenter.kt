package com.example.instagram.feature.home.presentation

import com.example.instagram.home.Home
import com.example.instagram.common.base.RequestCallback
import com.example.instagram.common.model.Post
import com.example.instagram.feature.home.data.HomeRepository

class HomePresenter(
    private val repository: HomeRepository
) {

    fun fetchFeed() {
        //TODO:view?.showProgress(true)
        repository.fetchFeed(object : RequestCallback<List<Post>> {
            override fun onSuccess(data: List<Post>) {
                if (data.isEmpty()) {
                    //TODO:view?.displayEmptyPosts()
                } else {
                    //TODO:view?.displayFullPosts(data)
                }
            }
            override fun onFailure(message: String) {
                //TODO:view?.displayRequestFailure(message)
            }
            override fun onComplete() {
                //TODO:view?.showProgress(false)
            }
        })
    }

    fun clear() {
        repository.clearCache()
    }

    fun logout() {
        repository.logout()
    }

}