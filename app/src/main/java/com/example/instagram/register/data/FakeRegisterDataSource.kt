package com.example.instagram.register.data

import android.os.Handler
import android.os.Looper
import com.example.instagram.common.model.Database
import com.example.instagram.common.model.UserAuth
import java.util.*

class FakeRegisterDataSource: RegisterDataSource {

    override fun register(email: String, callback: RegisterCallback) {
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

    override fun register(email: String, name: String, password: String, callback: RegisterCallback) {
        Handler(Looper.getMainLooper()).postDelayed({
            val userAuth = Database.usersAuth.firstOrNull{ email == it.email }
            if (userAuth != null){
                callback.onFailure("The user is already registered")
            } else{
                val isUserRegistered = Database.usersAuth.add(
                    UserAuth(UUID.randomUUID().toString(),name, email, password)
                )
                if (!isUserRegistered){
                    callback.onFailure("User registration failure due to an intern server problem")
                } else{
                    callback.onSuccess()
                }
                callback.onComplete()
            }
        }, 2000)
    }


}