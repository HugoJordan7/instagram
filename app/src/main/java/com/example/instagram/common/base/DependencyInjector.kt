package com.example.instagram.common.base

import com.example.instagram.login.data.FakeDataSource
import com.example.instagram.login.data.LoginRepository
import com.example.instagram.register.data.FakeRegisterEmailDataSource
import com.example.instagram.register.data.RegisterEmailRepository

object DependencyInjector {
    fun loginRepository(): LoginRepository{
        return LoginRepository(FakeDataSource())
    }
    fun registerEmailRepository(): RegisterEmailRepository{
        return RegisterEmailRepository(FakeRegisterEmailDataSource())
    }
}