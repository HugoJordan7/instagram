package com.example.instagram.common.model

import android.net.Uri
import java.io.File
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

        val filePath = "/storage/emulated/0/Android/media/com.example.instagram/Instagram/2024-07-24-14-12-29-261.jpeg"

        feeds[userA.uuid]?.addAll(
            arrayListOf(
                Post(
                    UUID.randomUUID().toString(),
                    Uri.fromFile(File(filePath)),
                    "caption",
                    System.currentTimeMillis(),
                    userA
                ),
                Post(
                    UUID.randomUUID().toString(),
                    Uri.fromFile(File(filePath)),
                    "caption",
                    System.currentTimeMillis(),
                    userA
                ),
                Post(
                    UUID.randomUUID().toString(),
                    Uri.fromFile(File(filePath)),
                    "caption",
                    System.currentTimeMillis(),
                    userA
                ),
                Post(
                    UUID.randomUUID().toString(),
                    Uri.fromFile(File(filePath)),
                    "caption",
                    System.currentTimeMillis(),
                    userA
                ),
                Post(
                    UUID.randomUUID().toString(),
                    Uri.fromFile(File(filePath)),
                    "caption",
                    System.currentTimeMillis(),
                    userA
                ),
                Post(
                    UUID.randomUUID().toString(),
                    Uri.fromFile(File(filePath)),
                    "caption",
                    System.currentTimeMillis(),
                    userA
                )
            )
        )
        feeds[userA.uuid]?.let {
            feeds[userB.uuid]?.addAll(it)
        }
    }

}