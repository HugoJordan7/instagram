package com.example.instagram.feature.search.presentation

import com.example.instagram.common.base.RequestCallback
import com.example.instagram.common.model.User
import com.example.instagram.common.model.UserAuth
import com.example.instagram.feature.search.Search
import com.example.instagram.feature.search.data.SearchRepository

class SearchPresenter(
    private var view: Search.View?,
    private val repository: SearchRepository
): Search.Presenter {

    override fun fetchUsers(name: String) {
        view?.showProgress(true)
        repository.fetchUsers(name, object : RequestCallback<List<User>>{
            override fun onSuccess(data: List<User>) {
                if(data.isEmpty()){
                    view?.displayEmptyUsers()
                } else{
                    view?.displayUsers(data)
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