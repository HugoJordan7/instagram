package com.example.instagram.feature.di.component

import com.example.instagram.feature.di.module.RegisterModule
import com.example.instagram.feature.register.view.FragmentRegisterEmail
import com.example.instagram.feature.register.view.FragmentRegisterNamePassword
import com.example.instagram.feature.register.view.FragmentRegisterPhoto
import com.example.instagram.feature.register.view.RegisterActivity
import dagger.Subcomponent

@Subcomponent(modules = [RegisterModule::class])
interface RegisterComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create(): RegisterComponent
    }

    fun inject(activity: RegisterActivity)
    fun inject(fragment: FragmentRegisterEmail)
    fun inject(fragment: FragmentRegisterNamePassword)
    fun inject(fragment: FragmentRegisterPhoto)

}