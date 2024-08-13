package com.example.instagram.feature.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.instagram.common.base.RequestCallback
import com.example.instagram.common.model.User
import com.example.instagram.feature.search.data.SearchRepository

class SearchViewModel(
    private val repository: SearchRepository
): ViewModel() {

    val users: LiveData<List<User>?> get() = _users
    private val _users = MutableLiveData<List<User>?>(null)

    val isLoading: LiveData<Boolean> get() = _isLoading
    private val _isLoading = MutableLiveData(false)

    val isFailure: LiveData<String?> get() = _isFailure
    private val _isFailure = MutableLiveData<String?>(null)

    fun fetchUsers(name: String) {
        _isLoading.value = true
        repository.fetchUsers(name, object : RequestCallback<List<User>>{
            override fun onSuccess(data: List<User>) {
                _users.value = data
                _isFailure.value = null
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