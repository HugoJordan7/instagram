package com.example.instagram.feature.login.data

import com.example.instagram.common.base.RequestCallback

class LoginRepository(private val dataSource: LoginDataSource) {
    fun login(email: String, password: String, callback: RequestCallback<Boolean>){
        dataSource.login(email, password, callback)
    }
}