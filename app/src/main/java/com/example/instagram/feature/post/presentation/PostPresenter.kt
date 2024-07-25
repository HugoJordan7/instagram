package com.example.instagram.feature.post.presentation

import com.example.instagram.feature.post.Post
import com.example.instagram.feature.post.data.PostRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class PostPresenter(
    private var view: Post.View?,
    private val repository: PostRepository
) : Post.Presenter, CoroutineScope {

    override val coroutineContext: CoroutineContext = Job() + Dispatchers.IO

    override fun fetchPictures() {
        view?.showProgress(true)
        launch {
            val photos = repository.fetchPictures()
            withContext(Dispatchers.Main){
                if (photos.isEmpty()){
                    view?.displayEmptyPictures()
                } else{
                    view?.displaysPictures(photos)
                }
                view?.showProgress(false)
            }
        }

    }

    override fun onDestroy() {
        view = null
        this.cancel()
    }

}