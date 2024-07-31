package com.example.instagram.common.di

import android.content.Context
import com.example.instagram.feature.add.data.AddFakeRemoteDataSource
import com.example.instagram.feature.add.data.AddLocalDataSource
import com.example.instagram.feature.add.data.AddRepository
import com.example.instagram.feature.home.data.FeedMemoryCache
import com.example.instagram.feature.home.data.HomeDataSourceFactory
import com.example.instagram.feature.home.data.HomeRepository
import com.example.instagram.feature.login.data.LoginRepository
import com.example.instagram.feature.login.data.RemoteLoginDataSource
import com.example.instagram.feature.post.data.LocalPostDataSource
import com.example.instagram.feature.post.data.PostRepository
import com.example.instagram.feature.profile.data.PostListMemoryCache
import com.example.instagram.feature.profile.data.ProfileDataSourceFactory
import com.example.instagram.feature.profile.data.ProfileMemoryCache
import com.example.instagram.feature.profile.data.ProfileRepository
import com.example.instagram.feature.register.data.RegisterRepository
import com.example.instagram.feature.register.data.RemoteRegisterDataSource
import com.example.instagram.feature.search.data.FakeRemoteSearchDataSource
import com.example.instagram.feature.search.data.SearchRepository
import com.example.instagram.feature.splash.data.RemoteSplashDataSource
import com.example.instagram.feature.splash.data.SplashRepository

object DependencyInjector {
    fun loginRepository(): LoginRepository {
        return LoginRepository(RemoteLoginDataSource())
    }
    fun registerRepository(): RegisterRepository {
        return RegisterRepository(RemoteRegisterDataSource())
    }

    fun splashRepository(): SplashRepository {
        return SplashRepository(RemoteSplashDataSource())
    }

    fun profileRepository(): ProfileRepository {
        return ProfileRepository(ProfileDataSourceFactory(ProfileMemoryCache, PostListMemoryCache))
    }

    fun homeRepository(): HomeRepository {
        return HomeRepository(HomeDataSourceFactory(FeedMemoryCache))
    }

    fun addRepository(): AddRepository {
        return AddRepository(AddLocalDataSource(), AddFakeRemoteDataSource())
    }

    fun postRepository(context: Context): PostRepository{
        return PostRepository(LocalPostDataSource(context))
    }

    fun searchRepository(): SearchRepository{
        return SearchRepository(FakeRemoteSearchDataSource())
    }

}