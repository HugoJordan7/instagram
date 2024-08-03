package com.example.instagram.feature.add.data

import android.net.Uri
import com.example.instagram.common.base.RequestCallback

class AddRepository(
    private val localDataSource: AddLocalDataSource,
    private val remoteDataSource: FireAddDataSource
) {

    fun createPost(uri: Uri, caption: String, callback: RequestCallback<Boolean>){
        val userUUID = localDataSource.fetchSession()
        remoteDataSource.createPost(userUUID, uri, caption, object : RequestCallback<Boolean>{
            override fun onSuccess(data: Boolean) {
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