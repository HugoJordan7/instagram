package com.example.instagram.common.model

import android.net.Uri
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.io.Serializable

data class User(
    var uuid: String? = null,
    var name: String? = null,
    var email: String? = null,
    var postsCount: Int = 0,
    var followersCount: Int = 0,
    var followingCount: Int = 0,
    var photoUri: String? = null
)