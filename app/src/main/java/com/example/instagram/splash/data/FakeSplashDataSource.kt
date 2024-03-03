package com.example.instagram.splash.data

import com.example.instagram.common.model.Database

class FakeSplashDataSource: SplashDataSource {
    override fun session(callback: SplashCallback) {
        if(Database.sessionAuth == null){
            callback.onFailure()
        } else{
            callback.onSuccess()
        }
    }
}