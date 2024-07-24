package com.example.instagram.feature.register.data

import android.net.Uri
import android.os.Handler
import android.os.Looper
import com.example.instagram.common.model.Database
import com.example.instagram.common.model.Photo
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
                val newUser = UserAuth(UUID.randomUUID().toString(),name, email, password,1,1,1)
                val isUserRegistered = Database.usersAuth.add(newUser)
                if (isUserRegistered){
                    Database.sessionAuth = newUser
                    Database.followers[newUser.uuid] = hashSetOf()
                    Database.posts[newUser.uuid] = hashSetOf()
                    Database.feeds[newUser.uuid] = hashSetOf()
                    callback.onSuccess()
                } else{
                    callback.onFailure("User registration failure due to an intern server problem")
                }
                callback.onComplete()
            }
        }, 2000)
    }

    override fun updateUser(uri: Uri, callback: RegisterCallback) {
        Handler(Looper.getMainLooper()).postDelayed({
            val userAuth = Database.sessionAuth
            if(userAuth == null){
                callback.onFailure("User not found")
            } else{
                val photo = Photo(userAuth.uuid,uri)
                val created = Database.photos.add(photo)
                if(created){
                    callback.onSuccess()
                } else{
                    callback.onFailure("Unknown error on created photo")
                }
            }
            callback.onComplete()
        },2000)
    }
}