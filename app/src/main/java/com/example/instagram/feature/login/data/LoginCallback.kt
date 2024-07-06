package com.example.instagram.feature.login.data

import com.example.instagram.common.model.UserAuth

interface LoginCallback {
    fun onSuccess(userAuth: UserAuth)
    fun onFailure(message: String)
    fun onComplete()
}