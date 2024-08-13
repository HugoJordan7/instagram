package com.example.instagram.feature.di.component

import com.example.instagram.feature.add.view.AddActivity
import com.example.instagram.feature.di.module.AddModule
import dagger.Subcomponent

@Subcomponent(modules = [AddModule::class])
interface AddComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create(): AddComponent
    }

    fun inject(activity: AddActivity)

}