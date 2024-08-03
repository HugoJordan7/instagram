package com.example.instagram.feature.add.data

import android.net.Uri
import com.example.instagram.common.base.RequestCallback
import com.example.instagram.common.model.Post
import com.example.instagram.common.model.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class FireAddDataSource: AddDataSource {

    override fun createPost(userUUID: String, uri: Uri, caption: String, callback: RequestCallback<Boolean>) {
        val uriLastPath = uri.lastPathSegment ?: throw IllegalArgumentException("Invalid image")

        val imageRef = FirebaseStorage.getInstance().reference
            .child("images/")
            .child(userUUID)
            .child(uriLastPath)

        imageRef.putFile(uri)
            .addOnSuccessListener { response ->
                imageRef.downloadUrl
                    .addOnSuccessListener { url ->
                        FirebaseFirestore.getInstance()
                            .collection("/users")
                            .document(userUUID)
                            .get()
                            .addOnSuccessListener { res ->

                                val me = res.toObject(User::class.java)

                                val postRef = FirebaseFirestore.getInstance()
                                    .collection("/posts")
                                    .document(userUUID)
                                    .collection("posts")
                                    .document()

                                val post = Post(
                                    uuid = postRef.id,
                                    photoUrl = url.toString(),
                                    caption = caption,
                                    timestamp = System.currentTimeMillis(),
                                    publisher = me
                                )

                                postRef.set(post)
                                    .addOnSuccessListener { postRes ->
                                        //add my post in my feed
                                        FirebaseFirestore.getInstance()
                                            .collection("/feeds")
                                            .document(userUUID)
                                            .collection("posts")
                                            .document(postRef.id)
                                            .set(post)
                                            .addOnSuccessListener { feedRes ->

                                                //add my post to my followers feed
                                                FirebaseFirestore.getInstance()
                                                    .collection("/followers")
                                                    .document(userUUID)
                                                    .get()
                                                    .addOnSuccessListener { followersRes ->
                                                        if(followersRes.exists()){
                                                            val followers = followersRes.get("followers") as List<String>
                                                            for (followerUUID in followers){
                                                                FirebaseFirestore.getInstance()
                                                                    .collection("/feeds")
                                                                    .document(followerUUID)
                                                                    .collection("posts")
                                                                    .document(postRef.id)
                                                                    .set(post)
                                                            }
                                                            callback.onSuccess(true)
                                                        }
                                                    }
                                                    .addOnFailureListener { exception ->
                                                        callback.onFailure(exception.message ?: "Erro ao buscar seguidores")
                                                    }
                                                    .addOnCompleteListener {
                                                        callback.onComplete()
                                                    }
                                            }
                                    }
                                    .addOnFailureListener { exception ->
                                        callback.onFailure(exception.message ?: "Erro ao setar post")
                                    }
                                    .addOnFailureListener { exception ->
                                        callback.onFailure(exception.message ?: "Falha ao inserir um post")
                                    }
                            }
                            .addOnFailureListener { exception ->
                                callback.onFailure(exception.message ?: "Erro ao buscar usuário logado")
                            }
                    }
                    .addOnFailureListener{ exception ->
                        callback.onFailure(exception.message ?: "Erro ao baixar imagem do post")
                    }
            }
            .addOnFailureListener{ exception ->
                callback.onFailure(exception.message ?: "Erro ao criar post")
            }
    }

}