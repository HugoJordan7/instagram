package com.example.instagram.register.data

import android.net.Uri

interface RegisterDataSource {
    fun register(email: String, callback: RegisterCallback)
    fun register(email: String, name: String, password: String, callback: RegisterCallback)
    fun updateUser(uri: Uri, callback: RegisterCallback)
}