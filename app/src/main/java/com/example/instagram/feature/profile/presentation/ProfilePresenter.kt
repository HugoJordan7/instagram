package com.example.instagram.feature.profile.presentation

import com.example.instagram.common.base.RequestCallback
import com.example.instagram.common.model.Post
import com.example.instagram.common.model.UserAuth
import com.example.instagram.feature.profile.Profile
import com.example.instagram.feature.profile.data.ProfileRepository

class ProfilePresenter(
    private var view: Profile.View?,
    private val repository: ProfileRepository
) : Profile.Presenter {

    var state: UserAuth? = null

    override fun fetchUserProfile() {
        view?.showProgress(true)
        repository.fetchUserProfile(object : RequestCallback<UserAuth>{
            override fun onSuccess(data: UserAuth) {
                state = data
                view?.displayUserProfile(data)
            }
            override fun onFailure(message: String) {
                view?.displayFailure(message)
            }
            override fun onComplete() {}
        })
    }

    override fun fetchUserPosts() {
        repository.fetchUserPosts(object : RequestCallback<List<Post>>{
            override fun onSuccess(data: List<Post>) {
                if(data.isNotEmpty()){
                    view?.displaysPosts(data)
                } else{
                    view?.displayEmptyPosts()
                }
            }
            override fun onFailure(message: String) {
                view?.displayFailure(message)
            }
            override fun onComplete() {
                view?.showProgress(false)
            }
        })
    }

    override fun onDestroy() {
        view = null
    }

}