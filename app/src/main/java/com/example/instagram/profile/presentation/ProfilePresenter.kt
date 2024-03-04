package com.example.instagram.profile.presentation

import com.example.instagram.profile.ProfileContract

class ProfilePresenter(
    private var view: ProfileContract.View?
) : ProfileContract.Presenter {

    override fun fetchUserProfile() {

    }

    override fun fetchUserPosts() {

    }

    override fun onDestroy() {
        view = null
    }

}