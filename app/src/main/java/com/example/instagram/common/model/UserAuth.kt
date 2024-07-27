package com.example.instagram.common.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

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