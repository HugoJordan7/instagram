package com.example.instagram.feature.profile.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.instagram.common.base.RequestCallback
import com.example.instagram.common.model.Post
import com.example.instagram.common.model.User
import com.example.instagram.feature.profile.data.ProfileRepository

class ProfileViewModel(
    private val repository: ProfileRepository
): ViewModel() {

    val posts: LiveData<List<Post>?> get() = _posts
    private val _posts = MutableLiveData<List<Post>?>(null)

    val isFollowUpdate: LiveData<Boolean> get() = _isFollowUpdate
    private val _isFollowUpdate = MutableLiveData(false)

    val displayUserProfile: LiveData<Pair<User, Boolean?>?> get() = _displayUserProfile
    private val _displayUserProfile = MutableLiveData<Pair<User, Boolean?>?>(null)

    val isLoading: LiveData<Boolean> get() = _isLoading
    private val _isLoading = MutableLiveData(false)

    val isFailure: LiveData<String?> get() = _isFailure
    private val _isFailure = MutableLiveData<String?>(null)

    fun followUser(uuid: String?, follow: Boolean) {
        repository.followUser(uuid, follow, object : RequestCallback<Boolean>{
            override fun onSuccess(data: Boolean) {
                fetchUserProfile(uuid)
                _isFollowUpdate.value = _isFollowUpdate.value?.not()
            }
            override fun onFailure(message: String) {}
            override fun onComplete() {}
        })
    }

    fun fetchUserProfile(uuid: String?) {
        _isLoading.value = true
        repository.fetchUserProfile(uuid, object : RequestCallback<Pair<User, Boolean?>>{
            override fun onSuccess(data: Pair<User, Boolean?>) {
                _displayUserProfile.value = data
                _isFailure.value = null
            }
            override fun onFailure(message: String) {
                _isFailure.value = message
            }
            override fun onComplete() {}
        })
    }

    fun fetchUserPosts(uuid: String?) {
        repository.fetchUserPosts(uuid, object : RequestCallback<List<Post>>{
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

}