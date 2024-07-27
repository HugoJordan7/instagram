package com.example.instagram.feature.profile.data

import com.example.instagram.common.base.Cache
import com.example.instagram.common.model.Post
import com.example.instagram.common.model.UserAuth

class ProfileDataSourceFactory(
    private val profileCache: Cache<UserAuth>,
    private val postsCache: Cache<List<Post>>
) {

    fun createLocalDataSource(): ProfileDataSource {
        return ProfileLocalDataSource(profileCache, postsCache)
    }

    fun createFromUser(uuid: String?): ProfileDataSource {
        if (uuid != null) return ProfileFakeRemoteDataSource()
        if(profileCache.isCached()) return ProfileLocalDataSource(profileCache, postsCache)
        return ProfileFakeRemoteDataSource()
    }

    fun createFromPosts(uuid: String?): ProfileDataSource {
        if (uuid != null) return ProfileFakeRemoteDataSource()
        if(postsCache.isCached()) return ProfileLocalDataSource(profileCache, postsCache)
        return ProfileFakeRemoteDataSource()
    }

}