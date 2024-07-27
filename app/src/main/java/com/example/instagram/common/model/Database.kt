package com.example.instagram.common.model

import android.net.Uri
import java.io.File
import java.util.*

object Database {

    var usersAuth = mutableListOf<UserAuth>()
    var posts = hashMapOf<String, MutableSet<Post>>()
    var feeds = hashMapOf<String, MutableSet<Post>>()
    val followers = hashMapOf<String, MutableSet<String>>()

    var sessionAuth: UserAuth? = null

    init {
        val filePath = "/storage/emulated/0/Android/media/com.example.instagram/Instagram/2024-07-26-11-34-13-453.jpeg"

        val userA = UserAuth(UUID.randomUUID().toString(), "User1","user1@gmail.com","12345678",0,5,5, Uri.fromFile(File(filePath)))
        val userB = UserAuth(UUID.randomUUID().toString(), "User2","user2@gmail.com","12345678",0,7,7, Uri.fromFile(File(filePath)))
        usersAuth.add(userA)
        usersAuth.add(userB)

        followers[userA.uuid] = hashSetOf()
        feeds[userA.uuid] = hashSetOf()
        posts[userA.uuid] = hashSetOf()

        followers[userB.uuid] = hashSetOf()
        feeds[userB.uuid] = hashSetOf()
        posts[userB.uuid] = hashSetOf()

        for (i in 0 .. 30){
            val user = UserAuth(UUID.randomUUID().toString(), "user$i", "user$i@gmail.com", "12344321", photoUri = null)
            usersAuth.add(user)
        }

        sessionAuth = usersAuth.first()
        followers[sessionAuth?.uuid]?.add(usersAuth[2].uuid)
    }

}