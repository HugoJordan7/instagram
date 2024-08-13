package com.example.instagram.feature.di.module

import androidx.lifecycle.ViewModel
import com.example.instagram.common.di.ViewModelKey
import com.example.instagram.feature.add.presentation.AddViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface AddModule {

    @Binds
    @IntoMap
    @ViewModelKey(AddViewModel::class)
    fun bindAddViewModel(viewModel: AddViewModel): ViewModel

}