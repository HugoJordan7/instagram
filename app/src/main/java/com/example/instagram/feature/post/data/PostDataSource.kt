package com.example.instagram.feature.post.data

import android.net.Uri

interface PostDataSource {
    suspend fun fetchPictures(): List<Uri>
}