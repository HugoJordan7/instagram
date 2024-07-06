package com.example.instagram.feature.home.data

import com.example.instagram.common.base.Cache
import com.example.instagram.common.model.Post


object FeedMemoryCache : Cache<List<Post>> {

    private var feeds: List<Post>? = null

    override fun isCached(): Boolean {
        return feeds != null
    }

    override fun get(key: String): List<Post>? {
        return feeds
    }

    override fun put(data: List<Post>) {
        feeds = data
    }

}