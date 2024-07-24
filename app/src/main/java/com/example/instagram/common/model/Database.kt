package com.example.instagram.common.model

import java.util.*

object Database {

    var usersAuth = hashSetOf<UserAuth>()
    var photos = hashSetOf<Photo>()
    var posts = hashMapOf<String,MutableSet<Post>>()
    var feeds = hashMapOf<String,MutableSet<Post>>()
    var sessionAuth: UserAuth? = null
    val followers = hashMapOf<String,MutableSet<String>>()

    init {
        val userA = UserAuth(UUID.randomUUID().toString(), "User1","user1@gmail.com","12345678",0,5,5)
        val userB = UserAuth(UUID.randomUUID().toString(), "User2","user2@gmail.com","12345678",0,7,7)
        usersAuth.add(userA)
        usersAuth.add(userB)

        followers[userA.uuid] = hashSetOf()
        feeds[userA.uuid] = hashSetOf()
        posts[userA.uuid] = hashSetOf()

        followers[userB.uuid] = hashSetOf()
        feeds[userB.uuid] = hashSetOf()
        posts[userB.uuid] = hashSetOf()

        sessionAuth = usersAuth.first()
    }

}