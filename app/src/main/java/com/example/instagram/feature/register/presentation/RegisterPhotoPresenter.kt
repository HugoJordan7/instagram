package com.example.instagram.feature.register.presentation

import android.net.Uri
import com.example.instagram.feature.register.RegisterPhotoContract
import com.example.instagram.feature.register.data.RegisterCallback
import com.example.instagram.feature.register.data.RegisterRepository

class RegisterPhotoPresenter(
    private var view: RegisterPhotoContract.View?,
    private val repository: RegisterRepository
) : RegisterPhotoContract.Presenter {

    override fun updateUser(uri: Uri) {
        view?.showProgress(true)
        repository.updateUser(uri, object : RegisterCallback {

            override fun onSuccess() {
                view?.onUpdateSuccess()
            }

            override fun onFailure(message: String) {
                view?.onUpdateFailure(message)
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