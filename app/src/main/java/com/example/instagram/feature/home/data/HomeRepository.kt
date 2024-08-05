package com.example.instagram.feature.home.data

import com.example.instagram.common.base.RequestCallback
import com.example.instagram.common.model.Post
import com.google.firebase.auth.FirebaseAuth

class HomeRepository(private val dataSourceFactory: HomeDataSourceFactory) {

    fun fetchFeed(callback: RequestCallback<List<Post>>) {
        val localDataSource = dataSourceFactory.createLocalDataSource()
        val userUUID = localDataSource.fetchSession()

        val dataSource = dataSourceFactory.createFromFeed()

        dataSource.fetchFeed(userUUID, object : RequestCallback<List<Post>> {
            override fun onSuccess(data: List<Post>) {
                localDataSource.putFeed(data)
                callback.onSuccess(data)
            }
            override fun onFailure(message: String) {
                callback.onFailure(message)
            }
            override fun onComplete() {
                callback.onComplete()
            }
        })
    }

    fun clearCache() {
        val localDataSource = dataSourceFactory.createLocalDataSource()
        localDataSource.putFeed(null)
    }

    fun logout(){
        dataSourceFactory.createRemoteDataSource().logout()
    }

}