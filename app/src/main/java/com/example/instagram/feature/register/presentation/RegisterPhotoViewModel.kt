package com.example.instagram.feature.register.presentation

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.instagram.common.base.RequestCallback
import com.example.instagram.feature.register.data.RegisterRepository

class RegisterPhotoViewModel(
    private val repository: RegisterRepository
) : ViewModel() {

    val isSuccess: LiveData<Boolean> get() = _isSuccess
    private val _isSuccess = MutableLiveData(false)

    val isLoading: LiveData<Boolean> get() = _isLoading
    private val _isLoading = MutableLiveData(false)

    val isFailure: LiveData<String?> get() = _isFailure
    private val _isFailure = MutableLiveData<String?>(null)

    fun updateUser(uri: Uri) {
        _isLoading.value = true
        repository.updateUser(uri, object : RequestCallback<Boolean> {
            override fun onSuccess(data: Boolean) {
                _isSuccess.value = true
            }
            override fun onFailure(message: String) {
                _isFailure.value = message
            }
            override fun onComplete() {
                _isLoading.value = false
            }
        })
    }

}