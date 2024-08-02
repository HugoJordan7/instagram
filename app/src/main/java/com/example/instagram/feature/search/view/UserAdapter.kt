package com.example.instagram.feature.search.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.R
import com.example.instagram.common.model.User
import com.squareup.picasso.Picasso

class UserAdapter(
    private val onClickListener: (String) -> Unit
): RecyclerView.Adapter<UserAdapter.UserViewHolder>(){

    var items: List<User> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_list,parent,false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(user: User){
            Picasso.get().load(user.photoUrl).into(itemView.findViewById<ImageView>(R.id.search_img_user))
            itemView.findViewById<TextView>(R.id.search_txt_username).text = user.name
            itemView.setOnClickListener {
                user.uuid?.let { onClickListener.invoke(it) }
            }
        }
    }
}