package com.example.instagram.feature.profile.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.R
import com.example.instagram.common.model.Post

class PostAdapter : RecyclerView.Adapter<PostAdapter.PostViewHolder>(){

    var posts: List<Post> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post_list,parent,false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(post: Post){
            itemView.findViewById<ImageView>(R.id.item_profile_img_grid).setImageURI(post.uri)
        }
    }

}