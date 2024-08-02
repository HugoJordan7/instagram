package com.example.instagram.feature.profile.data

import com.example.instagram.common.base.RequestCallback
import com.example.instagram.common.model.Post
import com.example.instagram.common.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class FireProfileRemoteDataSource: ProfileDataSource {

    override fun fetchUserProfile(userUUID: String, callback: RequestCallback<Pair<User, Boolean?>>) {
        FirebaseFirestore.getInstance()
            .collection("/users")
            .document(userUUID)
            .get()
            .addOnSuccessListener { response ->
                val user = response.toObject(User::class.java)
                if (user == null) {
                    callback.onFailure("Erro ao converter objeto de usuário")
                    return@addOnSuccessListener
                }

                if (FirebaseAuth.getInstance().uid == userUUID){
                    callback.onSuccess(user to null)
                } else {
                    FirebaseFirestore.getInstance()
                        .collection("/followers")
                        .document(FirebaseAuth.getInstance().uid!!)
                        .collection("followers")
                        .whereEqualTo("uuid", userUUID)
                        .get()
                        .addOnSuccessListener { res ->
                            callback.onSuccess(user to !res.isEmpty)
                        }
                        .addOnFailureListener{ exception ->
                            callback.onFailure(exception.message ?: "Erro ao buscar perfil")
                        }
                        .addOnCompleteListener {
                            callback.onComplete()
                        }
                }

            }
            .addOnFailureListener { exception ->
                callback.onFailure(exception.message ?: "Erro ao buscar perfil")
            }
    }

    override fun fetchUserPosts(userUUID: String, callback: RequestCallback<List<Post>>) {
        FirebaseFirestore.getInstance()
            .collection("/posts")
            .document(FirebaseAuth.getInstance().uid!!)
            .collection("posts")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { response ->
                val docs = response.documents
                val posts = mutableListOf<Post>()
                for (doc in docs){
                    val post = doc.toObject(Post::class.java)
                    post?.let { posts.add(it) }
                }
                callback.onSuccess(posts)
            }
            .addOnFailureListener { exception ->
                callback.onFailure(exception.message ?: "Erro ao buscar posts do perfil")
            }
            .addOnCompleteListener {
                callback.onComplete()
            }
    }

    override fun followUser(userUUID: String, isFollow: Boolean, callback: RequestCallback<Boolean>) {
        //TODO: after implements
    }

}