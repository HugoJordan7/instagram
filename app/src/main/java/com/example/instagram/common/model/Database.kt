package com.example.instagram.common.model

import java.util.*

object Database {

    var usersAuth = hashSetOf<UserAuth>()
    var sessionAuth: UserAuth? = null

    init {
        usersAuth.add(UserAuth(UUID.randomUUID().toString(), "User1","user1@gmail.com","12345678"))
        usersAuth.add(UserAuth(UUID.randomUUID().toString(), "User2","user2@gmail.com","12345678"))
    }

}