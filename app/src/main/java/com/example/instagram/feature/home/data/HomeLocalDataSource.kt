package com.example.instagram.feature.home.data

import com.example.instagram.common.base.Cache
import com.example.instagram.common.base.RequestCallback
import com.example.instagram.common.model.Post
import com.google.firebase.auth.FirebaseAuth

class HomeLocalDataSource(private val feedCache: Cache<List<Post>>) : HomeDataSource {

    override fun fetchFeed(userUUID: String, callback: RequestCallback<List<Post>>) {
        val feeds = feedCache.get(userUUID)
        if (feeds != null) {
            callback.onSuccess(feeds)
        } else {
            callback.onFailure("posts não existem")
        }
        callback.onComplete()
    }

    override fun fetchSession(): String {
        return FirebaseAuth.getInstance().uid ?: throw RuntimeException("usuário não logado!!!")
    }

    override fun putFeed(response: List<Post>?) {
        feedCache.put(response)
    }

}