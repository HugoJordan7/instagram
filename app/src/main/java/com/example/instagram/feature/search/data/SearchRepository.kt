package com.example.instagram.feature.search.data

import com.example.instagram.common.base.RequestCallback
import com.example.instagram.common.model.User

class SearchRepository(private val dataSource: SearchDataSource) {

    fun fetchUsers(name: String, callback: RequestCallback<List<User>>){
        dataSource.fetchUsers(name, object : RequestCallback<List<User>>{
            override fun onSuccess(data: List<User>) {
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

}