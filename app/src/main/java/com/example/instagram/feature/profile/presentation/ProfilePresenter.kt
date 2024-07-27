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

    override fun followUser(uuid: String?, follow: Boolean) {
        repository.followUser(uuid, follow, object : RequestCallback<Boolean>{
            override fun onSuccess(data: Boolean) {}
            override fun onFailure(message: String) {}
            override fun onComplete() {}
        })
    }

    override fun fetchUserProfile(uuid: String?) {
        view?.showProgress(true)
        repository.fetchUserProfile(uuid, object : RequestCallback<Pair<UserAuth, Boolean?>>{
            override fun onSuccess(data: Pair<UserAuth, Boolean?>) {
                view?.displayUserProfile(data)
            }
            override fun onFailure(message: String) {
                view?.displayFailure(message)
            }
            override fun onComplete() {}
        })
    }

    override fun fetchUserPosts(uuid: String?) {
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


    override fun clear() {
        repository.clearCache()
    }

    override fun onDestroy() {
        view = null
    }

}