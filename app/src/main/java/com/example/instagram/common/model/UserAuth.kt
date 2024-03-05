package com.example.instagram.common.model

data class UserAuth(
    var uuid: String,
    var name: String,
    var email: String,
    var password: String,
    var postsCount: Int,
    var followersCount: Int,
    var followingCount: Int
)
