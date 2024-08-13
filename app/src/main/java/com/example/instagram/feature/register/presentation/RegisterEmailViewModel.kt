package com.example.instagram.feature.register.presentation

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.instagram.R
import com.example.instagram.common.base.RequestCallback
import com.example.instagram.feature.register.RegisterEmailContract
import com.example.instagram.feature.register.data.RegisterRepository
import javax.inject.Inject

class RegisterEmailViewModel @Inject constructor(
    private val repository: RegisterRepository
) : ViewModel() {

    val isLoading: LiveData<Boolean> get() = _isLoading
    private val _isLoading = MutableLiveData(false)

    val isEmailFailure: LiveData<String?> get() = _isEmailFailure
    private val _isEmailFailure = MutableLiveData<String?>(null)

    val isEmailSuccess: LiveData<String?> get() = _isEmailSuccess
    private val _isEmailSuccess = MutableLiveData<String?>(null)

    fun register(email: String) {
        val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        _isEmailFailure.value = if (isEmailValid) null else "Email inválido"

        if(isEmailValid){
            _isLoading.value = true
            repository.register(email, object : RequestCallback<Boolean> {
                override fun onSuccess(data: Boolean) {
                    _isEmailSuccess.value = email
                    _isEmailFailure.value = null
                }
                override fun onFailure(message: String) {
                    _isEmailFailure.value = message
                }
                override fun onComplete() {
                    _isLoading.value = false
                }
            })
        }
    }

    fun onStart(){
        _isEmailSuccess.value = null
    }

}