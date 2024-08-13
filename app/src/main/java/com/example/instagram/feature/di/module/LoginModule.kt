package com.example.instagram.feature.di.module

import androidx.lifecycle.ViewModel
import com.example.instagram.common.di.ViewModelKey
import com.example.instagram.feature.login.presentation.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface LoginModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

}