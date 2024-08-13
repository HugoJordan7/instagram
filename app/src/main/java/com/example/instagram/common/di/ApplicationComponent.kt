package com.example.instagram.common.di

import android.content.Context
import com.example.instagram.feature.di.component.AddComponent
import com.example.instagram.feature.di.component.LoginComponent
import com.example.instagram.feature.di.component.MainComponent
import com.example.instagram.feature.di.component.SplashComponent
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelBuilderModule::class, SubcomponentsModule::class, RepositoryModule::class])
interface ApplicationComponent {

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }

    fun splashComponent(): SplashComponent.Factory

    fun mainComponent(): MainComponent.Factory

    fun addComponent(): AddComponent.Factory

    fun loginComponent(): LoginComponent.Factory

}

@Module(subcomponents = [SplashComponent::class, MainComponent::class, AddComponent::class, LoginComponent::class])
object SubcomponentsModule