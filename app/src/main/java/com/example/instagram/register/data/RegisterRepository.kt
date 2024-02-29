package com.example.instagram.register.data

import android.net.Uri

class RegisterRepository(private val dataSource: RegisterDataSource) {
    fun register(email: String, callback: RegisterCallback){
        dataSource.register(email, callback)
    }

    fun register(email: String, name: String, password: String, callback: RegisterCallback){
        dataSource.register(email, name, password, callback)
    }

    fun updateUser(uri: Uri, callback: RegisterCallback){
        dataSource.updateUser(uri,callback)
    }
}