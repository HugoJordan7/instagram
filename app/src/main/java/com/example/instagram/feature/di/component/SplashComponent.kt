package com.example.instagram.feature.di.component

import com.example.instagram.feature.di.module.SplashModule
import com.example.instagram.feature.splash.view.SplashActivity
import dagger.Subcomponent
import javax.inject.Scope

@Subcomponent(modules = [SplashModule::class])
interface SplashComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create(): SplashComponent
    }

    fun inject(activity: SplashActivity)

}