package com.example.instagram.register.data

import android.os.Handler
import android.os.Looper
import com.example.instagram.common.model.Database

class FakeRegisterEmailDataSource: RegisterEmailDataSource {

    override fun register(email: String, callback: RegisterEmailCallback) {
        Handler(Looper.getMainLooper()).postDelayed({
            val userAuth = Database.usersAuth.firstOrNull { email == it.email }
            if (userAuth != null){
                callback.onFailure("The user is already registered")
            } else{
                callback.onSuccess()
            }
            callback.onComplete()
        }, 2000)
    }
}