package com.example.instagram.feature.post.view

import android.net.Uri
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.R
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class PictureAdapter(
    private val onClick: (Uri) -> Unit
) : RecyclerView.Adapter<PictureAdapter.PostViewHolder>(), CoroutineScope{

    private val job = Job()
    override val coroutineContext: CoroutineContext = job + Dispatchers.IO

    var imageList: List<Uri> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_profile_grid,parent,false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        launch {
            holder.bind(imageList[position])
        }

    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    inner class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        suspend fun bind(image: Uri){
            //Picasso.with(context).load(image).resize(100,100).get();
            val bitmap = Picasso.get().load(image).resize(200,200).get()
            withContext(Dispatchers.Main){
                itemView.findViewById<ImageView>(R.id.item_profile_img_grid).setImageBitmap(bitmap)
                itemView.setOnClickListener {
                    onClick.invoke(image)
                }
            }
            //val bitmap = itemView.context.contentResolver.loadThumbnail(image, Size(200, 200), null)
        }
    }
}