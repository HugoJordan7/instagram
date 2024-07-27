package com.example.instagram.feature.profile.data

import com.example.instagram.common.base.RequestCallback
import com.example.instagram.common.model.Post
import com.example.instagram.common.model.UserAuth

interface ProfileDataSource {
    fun fetchUserProfile(userUUID: String, callback: RequestCallback<Pair<UserAuth, Boolean?>>)
    fun fetchUserPosts(userUUID: String, callback: RequestCallback<List<Post>>)
    fun fetchSession(): UserAuth { throw UnsupportedOperationException() }
    fun putUser(response: Pair<UserAuth, Boolean?>) { throw UnsupportedOperationException() }
    fun putPosts(response: List<Post>?){ throw UnsupportedOperationException() }

}