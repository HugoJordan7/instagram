package com.example.instagram.feature.home.data

import com.example.instagram.common.base.RequestCallback
import com.example.instagram.common.model.Post
import java.lang.UnsupportedOperationException

interface HomeDataSource {
    fun logout(){ throw UnsupportedOperationException() }

    fun fetchFeed(userUUID: String, callback: RequestCallback<List<Post>>)

    fun fetchSession() : String { throw UnsupportedOperationException() }

    fun putFeed(response: List<Post>?) { throw UnsupportedOperationException() }

}