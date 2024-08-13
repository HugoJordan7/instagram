package com.example.instagram.feature.di.module

import androidx.lifecycle.ViewModel
import com.example.instagram.common.di.ViewModelKey
import com.example.instagram.feature.register.presentation.RegisterEmailViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface RegisterModule {

    @Binds
    @IntoMap
    @ViewModelKey(RegisterEmailViewModel::class)
    fun bindRegisterEmailViewModel(viewModel: RegisterEmailViewModel): ViewModel

}