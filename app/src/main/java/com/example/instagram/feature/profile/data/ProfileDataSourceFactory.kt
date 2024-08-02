package com.example.instagram.feature.profile.data

import com.example.instagram.common.base.Cache
import com.example.instagram.common.model.Post
import com.example.instagram.common.model.User
import com.example.instagram.common.model.UserAuth

class ProfileDataSourceFactory(
    private val profileCache: Cache<Pair<User, Boolean?>>,
    private val postsCache: Cache<List<Post>>
) {

    fun createLocalDataSource(): ProfileDataSource {
        return ProfileLocalDataSource(profileCache, postsCache)
    }

    fun createRemoteDataSource(): ProfileDataSource {
        return FireProfileRemoteDataSource()
    }

    fun createFromUser(uuid: String?): ProfileDataSource {
        if (uuid != null) return FireProfileRemoteDataSource()
        if(profileCache.isCached()) return ProfileLocalDataSource(profileCache, postsCache)
        return FireProfileRemoteDataSource()
    }

    fun createFromPosts(uuid: String?): ProfileDataSource {
        if (uuid != null) return FireProfileRemoteDataSource()
        if(postsCache.isCached()) return ProfileLocalDataSource(profileCache, postsCache)
        return FireProfileRemoteDataSource()
    }

}