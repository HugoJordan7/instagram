package com.example.instagram.feature.home.view

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.R
import com.example.instagram.common.model.Post
import com.squareup.picasso.Picasso

class FeedAdapter : RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {

    var items = listOf<Post>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        return FeedViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_post_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class FeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(post: Post) {
            Picasso.get().load(post.photoUrl).into(itemView.findViewById<ImageView>(R.id.home_img_publication))
            //itemView.findViewById<ImageView>(R.id.home_img_publication).setImageURI(post.photoUrl)
            itemView.findViewById<TextView>(R.id.home_txt_caption).text = post.caption
            Picasso.get().load(post.publisher?.photoUrl).into(itemView.findViewById<ImageView>(R.id.home_img_profile))
            //itemView.findViewById<ImageView>(R.id.home_img_profile).setImageURI(post.publisher?.photoUrl)
            itemView.findViewById<TextView>(R.id.home_user_name).text = post.publisher?.name
        }
    }

}