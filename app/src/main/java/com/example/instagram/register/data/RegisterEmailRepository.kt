package com.example.instagram.register.data

class RegisterEmailRepository(private val dataSource: RegisterEmailDataSource) {
    fun register(email: String, callback: RegisterEmailCallback){
        dataSource.register(email, callback)
    }
}