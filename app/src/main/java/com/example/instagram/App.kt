package com.example.instagram

import android.app.Application
import com.example.instagram.common.di.ApplicationComponent
import com.example.instagram.common.di.DaggerApplicationComponent

class App: Application(){

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.factory().create(this)
    }

}