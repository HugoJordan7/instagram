package com.example.instagram.feature.add.data

import android.net.Uri
import com.example.instagram.common.base.RequestCallback

interface AddDataSource {
    fun createPost(userUUID: String, uri: Uri, caption: String, callback: RequestCallback<Boolean>){
        throw UnsupportedOperationException()
    }
    fun fetchSession(): String { throw UnsupportedOperationException() }
}