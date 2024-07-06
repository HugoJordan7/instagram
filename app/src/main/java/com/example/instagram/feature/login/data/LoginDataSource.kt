package com.example.instagram.feature.login.data

interface LoginDataSource {
    fun login(email: String, password: String, callback: LoginCallback)
}