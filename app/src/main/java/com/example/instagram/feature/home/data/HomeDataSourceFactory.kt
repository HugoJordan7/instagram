package com.example.instagram.feature.home.data

import com.example.instagram.common.base.Cache
import com.example.instagram.common.model.Post

class HomeDataSourceFactory(private val feedCache: Cache<List<Post>>) {

    fun createLocalDataSource(): HomeDataSource {
        return HomeLocalDataSource(feedCache)
    }

    fun createRemoteDataSource(): HomeDataSource {
        return FireHomeDataSource()
    }

    fun createFromFeed(): HomeDataSource {
        if (feedCache.isCached()) {
            return HomeLocalDataSource(feedCache)
        }
        return FireHomeDataSource()
    }

}