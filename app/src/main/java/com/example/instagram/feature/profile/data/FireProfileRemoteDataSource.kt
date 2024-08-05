package com.example.instagram.feature.profile.data

import com.example.instagram.common.base.RequestCallback
import com.example.instagram.common.model.Post
import com.example.instagram.common.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query

class FireProfileRemoteDataSource: ProfileDataSource {

    override fun fetchUserProfile(userUUID: String, callback: RequestCallback<Pair<User, Boolean?>>) {
        FirebaseFirestore.getInstance()
            .collection("/users")
            .document(userUUID)
            .get()
            .addOnSuccessListener { res ->
                val user = res.toObject(User::class.java)
                if (user == null) {
                    callback.onFailure("Erro ao converter objeto de usuário")
                    return@addOnSuccessListener
                }

                if (FirebaseAuth.getInstance().uid == userUUID){
                    callback.onSuccess(user to null)
                } else {
                    FirebaseFirestore.getInstance()
                        .collection("/followers")
                        .document(userUUID)
                        .get()
                        .addOnSuccessListener { response ->
                            if (!response.exists()) {
                                callback.onSuccess(Pair(user, false))
                            } else {
                                val list = response.get("followers") as List<String>
                                callback.onSuccess(Pair(user, list.contains(FirebaseAuth.getInstance().uid)))
                            }

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
            .document(userUUID)
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
        val currentUUID = FirebaseAuth.getInstance().uid ?: throw RuntimeException("Usuário não logado")
        FirebaseFirestore.getInstance()
            .collection("/followers")
            .document(userUUID)
            .update("followers",
                if (isFollow) FieldValue.arrayUnion(currentUUID)
                else FieldValue.arrayRemove(currentUUID)
            )
            .addOnSuccessListener { response ->
                followingCounter(currentUUID, isFollow)
                followersCounter(userUUID, callback)
                updateFeed(userUUID, isFollow)
            }
            .addOnFailureListener { exception ->
                val error = exception as? FirebaseFirestoreException
                if (error?.code == FirebaseFirestoreException.Code.NOT_FOUND){
                    FirebaseFirestore.getInstance()
                        .collection("/followers")
                        .document(userUUID)
                        .set(
                            hashMapOf("followers" to listOf(currentUUID))
                        )
                        .addOnSuccessListener { response ->
                            followingCounter(currentUUID, isFollow)
                            followersCounter(userUUID, callback)
                            updateFeed(userUUID, isFollow)
                        }
                        .addOnFailureListener {
                            callback.onFailure(exception.message ?: "Erro ao criar seguidor")
                        }
                } else{
                    callback.onFailure(exception.message ?: "Erro ao seguir usuário")
                }
            }
            .addOnCompleteListener {
                callback.onComplete()
            }
    }

    private fun followingCounter(uuid: String, isFollow: Boolean){
        val meRef = FirebaseFirestore.getInstance()
            .collection("/users")
            .document(uuid)

        if(isFollow) meRef.update("followingCount", FieldValue.increment(1))
        else meRef.update("followingCount", FieldValue.increment(-1))
    }

    private fun followersCounter(uuid: String, callback: RequestCallback<Boolean>){
        val meRef = FirebaseFirestore.getInstance()
            .collection("/users")
            .document(uuid)

        FirebaseFirestore.getInstance()
            .collection("/followers")
            .document(uuid)
            .get()
            .addOnSuccessListener { response ->
                if (response.exists()){
                    val followers = response.get("followers") as List<String>
                    meRef.update("followersCount", followers.size)
                }
                callback.onSuccess(true)
            }
    }

    private fun updateFeed(uuid: String, isFollow: Boolean){
        if (isFollow){
            FirebaseFirestore.getInstance()
                .collection("/posts")
                .document(uuid)
                .collection("posts")
                .get()
                .addOnSuccessListener { response ->
                    val posts = response.toObjects(Post::class.java)

                    posts.lastOrNull()?.let {
                        FirebaseFirestore.getInstance()
                            .collection("/feeds")
                            .document(FirebaseAuth.getInstance().uid!!)
                            .collection("posts")
                            .document(it.uuid!!)
                            .set(it)
                    }
                }
        } else{
            FirebaseFirestore.getInstance()
                .collection("/feeds")
                .document(FirebaseAuth.getInstance().uid!!)
                .collection("posts")
                .whereEqualTo("publisher.uuid", uuid)
                .get()
                .addOnSuccessListener { response ->
                    val docs = response.documents
                    for(doc in docs){
                        doc.reference.delete()
                    }
                }
        }
    }

}