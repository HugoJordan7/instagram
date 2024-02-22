package com.example.instagram.register.data

interface RegisterEmailDataSource {
    fun register(email: String, callback: RegisterEmailCallback)
}