package com.example.instagram.feature.di.component

import com.example.instagram.feature.di.module.LoginModule
import com.example.instagram.feature.login.view.LoginActivity
import dagger.Subcomponent

@Subcomponent(modules = [LoginModule::class])
interface LoginComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create(): LoginComponent
    }

    fun inject(activity: LoginActivity)

}