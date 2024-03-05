package com.example.instagram.profile.data

import android.os.Handler
import android.os.Looper
import com.example.instagram.common.base.RequestCallback
import com.example.instagram.common.model.Database
import com.example.instagram.common.model.Post
import com.example.instagram.common.model.UserAuth

class FakeProfileDataSource: ProfileDataSource {

    override fun fetchUserProfile(userUUID: String, callback: RequestCallback<UserAuth>) {
        Handler(Looper.getMainLooper()).postDelayed({
            val userAuth = Database.usersAuth.firstOrNull { it.uuid == userUUID }
            if(userAuth != null){
                callback.onSuccess(userAuth)
            } else{
                callback.onFailure("Profile not found")
            }
            callback.onComplete()
        },2000)
    }

    override fun fetchUserPosts(userUUID: String, callback: RequestCallback<List<Post>>) {
        Handler(Looper.getMainLooper()).postDelayed({
            val userAuth = Database.usersAuth.firstOrNull { it.uuid == userUUID }
            if(userAuth != null){
                val userPosts: List<Post> = emptyList()
                callback.onSuccess(userPosts)
            } else{
                callback.onFailure("Posts not found")
            }
            callback.onComplete()
        },2000)
    }

}