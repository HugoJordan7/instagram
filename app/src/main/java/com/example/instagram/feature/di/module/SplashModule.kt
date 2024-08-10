package com.example.instagram.feature.di.module

import androidx.lifecycle.ViewModel
import com.example.instagram.common.di.ViewModelKey
import com.example.instagram.feature.splash.presentation.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface SplashModule {

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    fun bindSplashViewModel(viewModel: SplashViewModel): ViewModel

}