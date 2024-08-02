package com.example.instagram.feature.profile.data

import android.os.Handler
import android.os.Looper
import com.example.instagram.common.base.RequestCallback
import com.example.instagram.common.model.Database
import com.example.instagram.common.model.Post
import com.example.instagram.common.model.User
import com.example.instagram.common.model.UserAuth

class ProfileFakeRemoteDataSource : ProfileDataSource {

    override fun fetchUserProfile(userUUID: String, callback: RequestCallback<Pair<User, Boolean?>>) {
        Handler(Looper.getMainLooper()).postDelayed({
            val userAuth = Database.usersAuth.firstOrNull { it.uuid == userUUID }
            if (userAuth != null) {
                if (userAuth.uuid == Database.sessionAuth?.uuid){
                    //callback.onSuccess(Pair(userAuth, null))
                } else {
                    val followers = Database.followers[Database.sessionAuth!!.uuid]
                    val destUser = followers?.firstOrNull{ it == userUUID }
                    //destUser != null -> Estou seguindo ele
                    //callback.onSuccess(Pair(userAuth, destUser != null))
                }
            } else {
                callback.onFailure("Profile not found")
            }
            callback.onComplete()
        }, 2000)
    }

    override fun fetchUserPosts(userUUID: String, callback: RequestCallback<List<Post>>) {
        Handler(Looper.getMainLooper()).postDelayed({
            val userPosts = Database.posts[userUUID] ?: emptySet()
            callback.onSuccess(userPosts.toList())
            callback.onComplete()
        }, 2000)
    }

    override fun followUser(userUUID: String, isFollow: Boolean, callback: RequestCallback<Boolean>) {
        Handler(Looper.getMainLooper()).postDelayed({
            var followers = Database.followers[Database.sessionAuth!!.uuid]
            if (followers == null){
                followers = mutableSetOf()
                Database.followers[Database.sessionAuth!!.uuid] = followers
            }

            if (isFollow){
                Database.followers[Database.sessionAuth!!.uuid]!!.add(userUUID)
            } else {
                Database.followers[Database.sessionAuth!!.uuid]!!.remove(userUUID)
            }

            callback.onSuccess(true)
            callback.onComplete()
        }, 500)
    }

}