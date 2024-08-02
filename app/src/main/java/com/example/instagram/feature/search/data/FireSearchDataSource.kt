package com.example.instagram.feature.search.data

import com.example.instagram.common.base.RequestCallback
import com.example.instagram.common.model.User
import com.example.instagram.common.model.UserAuth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FireSearchDataSource: SearchDataSource {

    override fun fetchUsers(name: String, callback: RequestCallback<List<UserAuth>>) {
        FirebaseFirestore.getInstance()
            .collection("/users")
            .whereGreaterThanOrEqualTo("name", name)
            .whereLessThanOrEqualTo("name", name + "\uf8ff")
            .get()
            .addOnSuccessListener { response ->
                val docs = response.documents
                val users = mutableListOf<User>()
                for (doc in docs){
                    val user = doc.toObject(User::class.java)
                    if(user != null && user.uuid != FirebaseAuth.getInstance().uid) {
                        users.add(user)
                    }
                }
                callback.onSuccess(users)
            }
            .addOnFailureListener { exception ->
                callback.onFailure(exception.message ?: "Falha ao buscar usuários")
            }
            .addOnCompleteListener {
                callback.onComplete()
            }
    }

}