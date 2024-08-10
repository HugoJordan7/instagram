package com.example.instagram.common.di

import com.example.instagram.feature.splash.data.SplashDataSource
import com.example.instagram.feature.splash.data.SplashRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideSplashRepository(dataSource: SplashDataSource): SplashRepository

}