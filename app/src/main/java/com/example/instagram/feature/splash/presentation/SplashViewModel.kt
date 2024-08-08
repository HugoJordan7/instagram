package com.example.instagram.feature.splash.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.instagram.common.base.RequestCallback
import com.example.instagram.feature.splash.data.SplashRepository

class SplashViewModel(private val repository: SplashRepository): ViewModel(){

    val isAuthenticated: LiveData<Boolean> get() = _isAuthenticated
    private val _isAuthenticated = MutableLiveData(false)

    val isFailure: LiveData<Boolean> get() = _isFailure
    private val _isFailure = MutableLiveData(false)

    fun authenticated() {
        repository.session(object : RequestCallback<Boolean> {
            override fun onSuccess(data: Boolean) {
                _isAuthenticated.value = data
            }
            override fun onFailure(message: String) {
                _isFailure.value = true
            }
            override fun onComplete() {}
        })
    }

    class ViewModelFactory(val repository: SplashRepository): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SplashViewModel::class.java)){
                return modelClass.getConstructor(SplashRepository::class.java).newInstance(repository)
            }
            throw IllegalArgumentException("Unknown view model")
        }
    }

}