package com.example.instagram.di

import com.example.instagram.login.data.FakeDataSource
import com.example.instagram.login.data.LoginRepository
import com.example.instagram.profile.data.PostListMemoryCache
import com.example.instagram.profile.data.ProfileDataSourceFactory
import com.example.instagram.profile.data.ProfileFakeRemoteDataSource
import com.example.instagram.profile.data.ProfileMemoryCache
import com.example.instagram.profile.data.ProfileRepository
import com.example.instagram.register.data.FakeRegisterDataSource
import com.example.instagram.register.data.RegisterRepository
import com.example.instagram.splash.data.FakeSplashDataSource
import com.example.instagram.splash.data.SplashRepository

object DependencyInjector {
    fun loginRepository(): LoginRepository{
        return LoginRepository(FakeDataSource())
    }
    fun registerRepository(): RegisterRepository{
        return RegisterRepository(FakeRegisterDataSource())
    }

    fun splashRepository(): SplashRepository {
        return SplashRepository(FakeSplashDataSource())
    }

    fun profileRepository(): ProfileRepository{
        return ProfileRepository(ProfileDataSourceFactory(ProfileMemoryCache, PostListMemoryCache))
    }
}