package com.example.instagram.common.model

import android.net.Uri
import java.io.File
import java.util.*

object Database {

    var usersAuth = mutableListOf<UserAuth>()
    var posts = hashMapOf<String,MutableSet<Post>>()
    var feeds = hashMapOf<String,MutableSet<Post>>()
    val followers = hashMapOf<String,MutableSet<String>>()

    var sessionAuth: UserAuth? = null

    init {
        val filePath = "/storage/emulated/0/Android/media/com.example.instagram/Instagram/2024-07-24-14-12-29-261.jpeg"

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

        sessionAuth = usersAuth.first()
    }

}