package com.example.instagram.common.model

data class User(
    var uuid: String? = null,
    var name: String? = null,
    var email: String? = null,
    var postCount: Int = 0,
    var followersCount: Int = 0,
    var followingCount: Int = 0,
    var photoUrl: String? = null
)