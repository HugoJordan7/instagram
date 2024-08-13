package com.example.instagram.feature.post.presentation

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.instagram.feature.post.data.PostRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class PostViewModel(
    private val repository: PostRepository
) : ViewModel(), CoroutineScope {

    private var uri: Uri? = null

    private val job = Job()
    override val coroutineContext: CoroutineContext = job + Dispatchers.IO

    val photos: LiveData<List<Uri>?> get() = _photos
    private val _photos = MutableLiveData<List<Uri>?>(null)

    val isLoading: LiveData<Boolean> get() = _isLoading
    private val _isLoading = MutableLiveData(false)

    fun fetchPictures() {
        _isLoading.value = true
        launch {
            val photos: List<Uri> = repository.fetchPictures()
            withContext(Dispatchers.Main){
                _photos.value = photos
                _isLoading.value = false
                this.cancel()
            }
        }

    }

    fun getSelectedUri(): Uri? = uri

    fun selectUri(uri: Uri) {
        this.uri = uri
    }

}