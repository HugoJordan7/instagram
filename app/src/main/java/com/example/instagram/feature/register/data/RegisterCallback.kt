package com.example.instagram.feature.register.data

interface RegisterCallback {
    fun onSuccess()
    fun onFailure(message: String)
    fun onComplete()
}