package com.example.instagram.register.data

interface RegisterDataSource {
    fun register(email: String, callback: RegisterCallback)
    fun register(email: String, name: String, password: String, callback: RegisterCallback)
}