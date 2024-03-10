package com.example.instagram.profile.presentation

import com.example.instagram.common.base.RequestCallback
import com.example.instagram.common.model.Database
import com.example.instagram.common.model.Post
import com.example.instagram.common.model.UserAuth
import com.example.instagram.profile.ProfileContract
import com.example.instagram.profile.data.ProfileRepository

class ProfilePresenter(
    private var view: ProfileContract.View?,
    private val repository: ProfileRepository
) : ProfileContract.Presenter {

    override var state: UserAuth? = null

    override fun fetchUserProfile() {
        view?.showProgress(true)
        val uuid = Database.sessionAuth?.uuid ?: throw Exception("User uuid not found")
        repository.fetchUserProfile(uuid, object : RequestCallback<UserAuth>{

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
        val uuid = Database.sessionAuth?.uuid ?: throw Exception("User uuid not found")
        repository.fetchUserPosts(uuid, object : RequestCallback<List<Post>>{

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