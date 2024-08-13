package com.example.instagram.feature.add.presentation

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.instagram.common.base.RequestCallback
import com.example.instagram.feature.add.data.AddRepository

class AddViewModel(
    private val repository: AddRepository
): ViewModel() {

    val isSuccess: LiveData<Boolean> get() = _isSuccess
    private val _isSuccess = MutableLiveData(false)

    val isLoading: LiveData<Boolean> get() = _isLoading
    private val _isLoading = MutableLiveData(false)

    val isFailure: LiveData<String?> get() = _isFailure
    private val _isFailure = MutableLiveData<String?>(null)

    fun createPost(uri: Uri, caption: String) {
        _isLoading.value = true
        repository.createPost(uri, caption, object : RequestCallback<Boolean>{
            override fun onSuccess(data: Boolean) {
                if (data){
                    _isSuccess.value = true
                    _isFailure.value = null
                } else{
                    _isSuccess.value = false
                    _isFailure.value = "Internal error"
                }
            }
            override fun onFailure(message: String) {
                _isSuccess.value = false
                _isFailure.value = message
            }
            override fun onComplete() {
                _isLoading.value = false
            }

        })
    }

}