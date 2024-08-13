package com.example.instagram.feature.register.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.instagram.R
import com.example.instagram.common.base.RequestCallback
import com.example.instagram.feature.register.RegisterNamePasswordContract
import com.example.instagram.feature.register.data.RegisterRepository

class RegisterNamePasswordViewModel(
    private val repository: RegisterRepository
) : ViewModel() {

    val isEmailSuccess: LiveData<String?> get() = _isEmailSuccess
    private val _isEmailSuccess = MutableLiveData<String?>(null)

    val isLoading: LiveData<Boolean> get() = _isLoading
    private val _isLoading = MutableLiveData(false)

    val isNameFailure: LiveData<Int?> get() = _isNameFailure
    private val _isNameFailure = MutableLiveData<Int?>(null)

    val isPasswordFailure: LiveData<Int?> get() = _isPasswordFailure
    private val _isPasswordFailure = MutableLiveData<Int?>(null)

    val isFailure: LiveData<String?> get() = _isFailure
    private val _isFailure = MutableLiveData<String?>(null)

    fun register(email: String, name: String, password: String, confirm: String) {
        val isNameValid = name.length >= 4
        val isPasswordValid = password.length >= 8
        val isConfirmValid = password == confirm
        _isNameFailure.value = if (isNameValid) null else R.string.invalid_name
        _isPasswordFailure.value = if (isPasswordValid) null else R.string.invalid_password
        if (isPasswordValid) {
            _isPasswordFailure.value = if (isConfirmValid) null else R.string.password_not_equal
        }

        if (isNameValid && isPasswordValid && isConfirmValid) {
            _isLoading.value = true
            repository.register(email, name, password, object : RequestCallback<Boolean> {
                override fun onSuccess(data: Boolean) {
                    _isEmailSuccess.value = name
                    _isFailure.value = null
                }

                override fun onFailure(message: String) {
                    _isEmailSuccess.value = null
                    _isFailure.value = message
                }

                override fun onComplete() {
                    _isLoading.value = false
                }
            })
        }
    }

}