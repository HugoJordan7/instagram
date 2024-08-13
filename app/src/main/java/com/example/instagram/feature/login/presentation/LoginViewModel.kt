package com.example.instagram.feature.login.presentation

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.instagram.R
import com.example.instagram.common.base.RequestCallback
import com.example.instagram.feature.login.data.LoginRepository

class LoginViewModel(
    private val repository: LoginRepository
) : ViewModel(){

    val isAuthenticated: LiveData<Boolean> get() = _isAuthenticated
    private val _isAuthenticated = MutableLiveData(false)

    val isLoading: LiveData<Boolean> get() = _isLoading
    private val _isLoading = MutableLiveData(false)

    val isEmailFailure: LiveData<Int?> get() = _isEmailFailure
    private val _isEmailFailure = MutableLiveData<Int?>(null)

    val isPasswordFailure: LiveData<Int?> get() = _isPasswordFailure
    private val _isPasswordFailure = MutableLiveData<Int?>(null)

    val isUserUnauthorized: LiveData<String?> get() = _isUserUnauthorized
    private val _isUserUnauthorized = MutableLiveData<String?>(null)

    fun loginValidate(email: String, password: String) {
        val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val isPasswordValid = password.length >= 8
        _isEmailFailure.value = if (isEmailValid) null else R.string.invalid_email
        _isPasswordFailure.value = if (isPasswordValid) null else R.string.invalid_password

        if(isEmailValid && isPasswordValid){
            _isLoading.value = true
            repository.login(email,password, object : RequestCallback<Boolean> {
                override fun onSuccess(data: Boolean) {
                    _isAuthenticated.value = data
                }
                override fun onFailure(message: String) {
                    _isUserUnauthorized.value = message
                }
                override fun onComplete() {
                    _isLoading.value = false
                }
            })
        }
    }

}