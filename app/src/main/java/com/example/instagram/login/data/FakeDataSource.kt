package com.example.instagram.login.data

import android.os.Handler
import android.os.Looper

class FakeDataSource: LoginDataSource {
    override fun login(email: String, password: String, callback: LoginCallback) {
        Handler(Looper.getMainLooper()).postDelayed({
            if(email == "hugo@gmail.com" && password == "12344321"){
                callback.onSuccess()
            } else{
                callback.onFailure("Invalid login!")
            }
            callback.onComplete()
        }, 2000)
    }
}