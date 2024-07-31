package com.example.instagram.feature.splash.data

import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class RemoteSplashDataSource: SplashDataSource {

    override fun session(callback: SplashCallback) {
        if(FirebaseAuth.getInstance().uid != null){
            callback.onSuccess()
        } else {
            callback.onFailure()
        }
    }

}