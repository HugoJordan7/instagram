package com.example.instagram.common.di

import android.content.Context
import com.example.instagram.feature.di.component.SplashComponent
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Singleton
@Component(modules = [RepositoryModule::class, ViewModelBuilderModule::class, SubcomponentsModule::class])
interface ApplicationComponent {

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }

    fun splashComponent(): SplashComponent.Factory

}

@Module(subcomponents = [SplashComponent::class])
object SubcomponentsModule