package com.example.instagram.feature.search.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.R

class PostAdapter : RecyclerView.Adapter<PostAdapter.PostViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_list,parent,false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(R.drawable.ic_insta_add)
    }

    override fun getItemCount() = 30

    inner class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(image: Int){
            val imageView = itemView.findViewById<ImageView>(R.id.search_img_user)
            imageView.setImageResource(image)
        }
    }
}