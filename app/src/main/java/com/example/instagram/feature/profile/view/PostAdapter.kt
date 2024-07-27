package com.example.instagram.feature.profile.view

import android.net.Uri
import android.util.Log
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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_profile_grid, parent,false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(posts[position].uri)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(image: Uri){
            itemView.findViewById<ImageView>(R.id.item_profile_img_grid).setImageURI(image)
        }
    }

}