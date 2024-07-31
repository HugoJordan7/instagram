package com.example.instagram.feature.login.data

import android.os.Handler
import android.os.Looper
import com.example.instagram.common.base.RequestCallback
import com.example.instagram.common.model.Database

class FakeDataSource: LoginDataSource {
    override fun login(email: String, password: String, callback: RequestCallback<Boolean>) {
        Handler(Looper.getMainLooper()).postDelayed({
            val userAuth = Database.usersAuth.firstOrNull{
                email == it.email
            }
            when {
                userAuth == null -> {
                    callback.onFailure("User not found!")
                }
                userAuth.password != password -> {
                    callback.onFailure("The password is incorrect!")
                }
                else -> {
                    Database.sessionAuth = userAuth
                    callback.onSuccess(true)
                }
            }
            callback.onComplete()
        }, 2000)
    }
}