package com.example.instagram.common.model

import android.net.Uri

data class UserAuth(
    var uuid: String,
    var name: String,
    var email: String,
    var password: String,
    var postsCount: Int = 0,
    var followersCount: Int = 0,
    var followingCount: Int = 0,
    var photoUri: Uri?
)