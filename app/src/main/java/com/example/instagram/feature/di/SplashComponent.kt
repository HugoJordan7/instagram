package com.example.instagram.feature.di

import com.example.instagram.feature.splash.view.SplashActivity
import dagger.Subcomponent

@Subcomponent(modules = [])
interface SplashComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create(): SplashComponent
    }

    fun inject(activity: SplashActivity)

}