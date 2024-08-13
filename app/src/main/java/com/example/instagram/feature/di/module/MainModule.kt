package com.example.instagram.feature.di.module

import androidx.lifecycle.ViewModel
import com.example.instagram.common.di.ViewModelKey
import com.example.instagram.feature.home.presentation.HomeViewModel
import com.example.instagram.feature.profile.presentation.ProfileViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface MainModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    fun bindProfileViewModel(viewModel: ProfileViewModel): ViewModel

}