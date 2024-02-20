package com.example.instagram.common.base

interface BasePresenter<T> {
    var view: T?
    fun onDestroy()
}