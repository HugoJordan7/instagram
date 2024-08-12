package com.example.instagram.common.di

import android.content.Context
import com.example.instagram.feature.add.data.AddLocalDataSource
import com.example.instagram.feature.add.data.AddRepository
import com.example.instagram.feature.add.data.FireAddDataSource
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
import com.example.instagram.feature.search.data.FireSearchDataSource
import com.example.instagram.feature.search.data.SearchRepository
import com.example.instagram.feature.splash.data.RemoteSplashDataSource
import com.example.instagram.feature.splash.data.SplashRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideSplashRepository(): SplashRepository{
        return SplashRepository(RemoteSplashDataSource())
    }

    @Provides
    fun provideLoginRepository(): LoginRepository {
        return LoginRepository(RemoteLoginDataSource())
    }

    @Provides
    fun provideRegisterRepository(): RegisterRepository {
        return RegisterRepository(RemoteRegisterDataSource())
    }

    @Provides
    fun provideProfileRepository(): ProfileRepository {
        return ProfileRepository(ProfileDataSourceFactory(ProfileMemoryCache, PostListMemoryCache))
    }

    @Provides
    fun provideHomeRepository(): HomeRepository {
        return HomeRepository(HomeDataSourceFactory(FeedMemoryCache))
    }

    @Provides
    fun provideAddRepository(): AddRepository {
        return AddRepository(AddLocalDataSource(), FireAddDataSource())
    }

    @Provides
    fun providePostRepository(context: Context): PostRepository {
        return PostRepository(LocalPostDataSource(context))
    }

    @Provides
    fun provideSearchRepository(): SearchRepository {
        return SearchRepository(FireSearchDataSource())
    }

}