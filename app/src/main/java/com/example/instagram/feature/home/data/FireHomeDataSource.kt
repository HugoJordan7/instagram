package com.example.instagram.feature.home.data

import com.example.instagram.common.base.RequestCallback
import com.example.instagram.common.model.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class FireHomeDataSource: HomeDataSource {

    override fun fetchFeed(userUUID: String, callback: RequestCallback<List<Post>>) {
        val uuid = FirebaseAuth.getInstance().uid ?: throw RuntimeException("O usuário não esta logado")
        FirebaseFirestore.getInstance()
            .collection("/feeds")
            .document(uuid)
            .collection("/posts")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { response ->
                val feed = mutableListOf<Post>()
                val docs = response.documents
                for (doc in docs){
                    val post = doc.toObject(Post::class.java)
                    post?.let { feed.add(it) }
                }
                callback.onSuccess(feed)
            }
            .addOnFailureListener { exception ->
                callback.onFailure(exception.message ?: "Erro ao carregar o feed")
            }
            .addOnCompleteListener {
                callback.onComplete()
            }
    }

    override fun logout(){
        FirebaseAuth.getInstance().signOut()
    }

}