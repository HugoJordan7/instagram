package com.example.instagram.feature.splash.data

import com.example.instagram.common.base.RequestCallback

interface SplashDataSource {
    fun session(callback: RequestCallback<Boolean>)
}