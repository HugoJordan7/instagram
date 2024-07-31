package com.example.instagram.feature.splash.data

import com.example.instagram.common.base.RequestCallback
import com.google.firebase.auth.FirebaseAuth

class RemoteSplashDataSource: SplashDataSource {

    override fun session(callback: RequestCallback<Boolean>) {
        if(FirebaseAuth.getInstance().uid != null){
            callback.onSuccess(true)
        } else {
            callback.onFailure("")
        }
    }

}