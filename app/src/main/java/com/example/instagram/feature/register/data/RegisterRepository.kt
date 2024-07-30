package com.example.instagram.feature.register.data

import android.net.Uri
import com.example.instagram.common.base.RequestCallback

class RegisterRepository(private val dataSource: RegisterDataSource) {
    fun register(email: String, callback: RequestCallback<Boolean>){
        dataSource.register(email, callback)
    }

    fun register(email: String, name: String, password: String, callback: RequestCallback<Boolean>){
        dataSource.register(email, name, password, callback)
    }

    fun updateUser(uri: Uri, callback: RequestCallback<Boolean>){
        dataSource.updateUser(uri,callback)
    }
}