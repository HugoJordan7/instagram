package com.example.instagram.feature.profile.data

import com.example.instagram.common.base.RequestCallback
import com.example.instagram.common.model.Database
import com.example.instagram.common.model.Post
import com.example.instagram.common.model.UserAuth

class ProfileLocalDataSource(
    private val profileCache: ProfileCache<UserAuth>,
    private val postsCache: ProfileCache<List<Post>>
): ProfileDataSource {
    override fun fetchUserProfile(userUUID: String, callback: RequestCallback<UserAuth>) {
        val userAuth = profileCache.get(userUUID)
        if(userAuth != null){
            callback.onSuccess(userAuth)
        } else{
            callback.onFailure("Usuário não encontrado")
        }
        callback.onComplete()
    }

    override fun fetchUserPosts(userUUID: String, callback: RequestCallback<List<Post>>) {
        val posts = postsCache.get(userUUID)
        if(posts != null){
            callback.onSuccess(posts)
        } else{
            callback.onFailure("Postagens não encontradas")
        }
        callback.onComplete()
    }

    override fun fetchSession(): UserAuth {
        return Database.sessionAuth ?: throw RuntimeException("Usuário não logado")
    }

    override fun putUser(response: UserAuth) {
        profileCache.put(response)
    }

    override fun putPosts(response: List<Post>) {
        postsCache.put(response)
    }

}