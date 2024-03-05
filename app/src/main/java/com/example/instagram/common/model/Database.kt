package com.example.instagram.common.model

import java.util.*

object Database {

    var usersAuth = hashSetOf<UserAuth>()
    var photos = hashSetOf<Photo>()
    var sessionAuth: UserAuth? = null

    init {
        usersAuth.add(UserAuth(UUID.randomUUID().toString(), "User1","user1@gmail.com","12345678",0,5,5))
        usersAuth.add(UserAuth(UUID.randomUUID().toString(), "User2","user2@gmail.com","12345678",0,7,7))
        sessionAuth = usersAuth.first()
    }

}