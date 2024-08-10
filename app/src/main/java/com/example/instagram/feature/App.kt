package com.example.instagram.feature

import android.app.Application
import com.example.instagram.common.di.ApplicationComponent

class App: Application(){

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        //applicationComponent = Dagger
    }

}