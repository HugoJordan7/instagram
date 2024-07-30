package com.example.instagram.feature.register.data

import android.net.Uri
import com.example.instagram.common.base.RequestCallback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RemoteRegisterDataSource: RegisterDataSource {

    override fun register(email: String, callback: RequestCallback<Boolean>) {
        FirebaseFirestore.getInstance()
            .collection("/users")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { response ->
                if (response.isEmpty){
                    callback.onSuccess(true)
                } else{
                    callback.onFailure("Usuário já cadastrado!")
                }
            }
            .addOnFailureListener{ exception ->
                callback.onFailure(exception.message ?: "Erro interno do servidor!")
            }
            .addOnCompleteListener {
                callback.onComplete()
            }
    }

    override fun register(email: String, name: String, password: String, callback: RequestCallback<Boolean>) {
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { response ->
                val uuid = response.user?.uid
                if (uuid == null){
                    callback.onFailure("Erro interno do servidor!")
                    return@addOnSuccessListener
                }

                FirebaseFirestore.getInstance()
                    .collection("/users")
                    .document(uuid)
                    .set(
                        hashMapOf(
                            "name" to name,
                            "email" to email,
                            "postsCount" to 0,
                            "followersCount" to 0,
                            "followingCount" to 0,
                            "photoUri" to null
                        )
                    )
                    .addOnSuccessListener {
                        callback.onSuccess(true)
                    }
                    .addOnFailureListener { exception ->
                        callback.onFailure(exception.message ?: "Erro interno do servidor!")
                    }
                    .addOnCompleteListener {
                        callback.onComplete()
                    }
            }
            .addOnFailureListener{ exception ->
                callback.onFailure(exception.message ?: "Erro interno do servidor!")
            }
    }

    override fun updateUser(uri: Uri, callback: RequestCallback<Boolean>) {
        TODO("Not yet implemented")
    }

}