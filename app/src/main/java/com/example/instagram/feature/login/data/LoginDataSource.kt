package com.example.instagram.feature.login.data

import com.example.instagram.common.base.RequestCallback

interface LoginDataSource {
    fun login(email: String, password: String, callback: RequestCallback<Boolean>)
}