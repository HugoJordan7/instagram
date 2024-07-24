package com.example.instagram.feature.add.presentation

import android.net.Uri
import com.example.instagram.common.base.RequestCallback
import com.example.instagram.feature.add.Add
import com.example.instagram.feature.add.data.AddRepository

class AddPresenter(
    private var view: Add.View?,
    private val repository: AddRepository
): Add.Presenter {
    override fun createPost(uri: Uri, caption: String) {
        view?.showProgress(true)
        repository.createPost(uri, caption, object : RequestCallback<Boolean>{
            override fun onSuccess(data: Boolean) {
                if (data){
                    view?.displaySuccess()
                } else{
                    view?.displayFailure("Internal error")
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