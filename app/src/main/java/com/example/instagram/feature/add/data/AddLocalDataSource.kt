package com.example.instagram.feature.add.data

import com.example.instagram.common.model.Database
import com.example.instagram.common.model.UserAuth

class AddLocalDataSource: AddDataSource {

    override fun fetchSession(): UserAuth {
        return Database.sessionAuth ?: throw RuntimeException("Usuário não logado")
    }

}