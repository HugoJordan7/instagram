package com.example.instagram.feature.login.data

import com.example.instagram.common.base.RequestCallback
import com.google.firebase.auth.FirebaseAuth

class RemoteLoginDataSource: LoginDataSource {

    override fun login(email: String, password: String, callback: RequestCallback<Boolean>) {
        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { response ->
                if(response.user == null){
                    callback.onFailure("O usuário ainda não foi cadastrado")
                    return@addOnSuccessListener
                }
                callback.onSuccess(true)
            }
            .addOnFailureListener { exception ->
                callback.onFailure(exception.message ?: "Erro ao fazer login")
            }
            .addOnCompleteListener {
                callback.onComplete()
            }
    }

}