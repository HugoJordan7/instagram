package com.example.instagram.feature.register.data

import android.net.Uri
import com.example.instagram.common.base.RequestCallback

interface RegisterDataSource {
    fun register(email: String, callback: RequestCallback<Boolean>)
    fun register(email: String, name: String, password: String, callback: RequestCallback<Boolean>)
    fun updateUser(uri: Uri, callback: RequestCallback<Boolean>)
}