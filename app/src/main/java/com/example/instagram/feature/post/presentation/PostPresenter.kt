package com.example.instagram.feature.post.presentation

import android.net.Uri
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

    private var uri: Uri? = null

    private val job = Job()
    override val coroutineContext: CoroutineContext = job + Dispatchers.IO

    override fun fetchPictures() {
        view?.showProgress(true)
        launch {
            val photos = repository.fetchPictures()
            withContext(Dispatchers.Main){
                if (photos.isEmpty()){
                    view?.displayEmptyPictures()
                } else{
                    view?.displayPictures(photos)
                }
                view?.showProgress(false)
            }
        }

    }

    override fun getSelectedUri(): Uri? = uri

    override fun selectUri(uri: Uri) {
        this.uri = uri
    }

    override fun onDestroy() {
        view = null
        job.cancel()
    }

}