package com.example.instagram.feature.add.data

import com.google.firebase.auth.FirebaseAuth

class AddLocalDataSource: AddDataSource {

    override fun fetchSession(): String {
        return FirebaseAuth.getInstance().uid ?: throw RuntimeException("Usuário não logado")
    }

}