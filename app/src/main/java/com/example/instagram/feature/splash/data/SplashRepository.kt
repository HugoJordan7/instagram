package com.example.instagram.feature.splash.data

import com.example.instagram.common.base.RequestCallback

class SplashRepository(private val dataSource: SplashDataSource) {
    fun session(callback: RequestCallback<Boolean>){
        dataSource.session(callback)
    }
}