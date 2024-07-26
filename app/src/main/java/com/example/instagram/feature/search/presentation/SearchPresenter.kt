package com.example.instagram.feature.search.presentation

import com.example.instagram.feature.search.Search

class SearchPresenter(private var view: Search.View?): Search.Presenter {


    override fun onDestroy() {
        view = null
    }

}