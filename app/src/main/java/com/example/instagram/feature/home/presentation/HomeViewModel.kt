package com.example.instagram.feature.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.instagram.common.base.RequestCallback
import com.example.instagram.common.model.Post
import com.example.instagram.feature.home.data.HomeRepository
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val repository: HomeRepository): ViewModel() {

    val posts: LiveData<List<Post>> get() = _posts
    private val _posts = MutableLiveData(emptyList<Post>())

    val isLoading: LiveData<Boolean> get() = _isLoading
    private val _isLoading = MutableLiveData(false)

    val isFailure: LiveData<String?> get() = _isFailure
    private val _isFailure = MutableLiveData<String?>(null)


    fun fetchFeed() {
        _isLoading.value = true
        repository.fetchFeed(object : RequestCallback<List<Post>> {
            override fun onSuccess(data: List<Post>) {
                _posts.value = data
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

    fun clear() {
        repository.clearCache()
    }

    fun logout() {
        repository.logout()
    }

}