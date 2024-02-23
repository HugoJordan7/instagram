package com.example.instagram.register.data

class RegisterRepository(private val dataSource: RegisterDataSource) {
    fun register(email: String, callback: RegisterCallback){
        dataSource.register(email, callback)
    }

    fun register(email: String, name: String, password: String, callback: RegisterCallback){
        dataSource.register(email, name, password, callback)
    }
}