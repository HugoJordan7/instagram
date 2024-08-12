package com.example.instagram.feature.splash.data

import com.example.instagram.common.base.RequestCallback
import javax.inject.Inject

class SplashRepository(
    private val dataSource: RemoteSplashDataSource
) {

    fun session(callback: RequestCallback<Boolean>){
        dataSource.session(callback)
    }

}