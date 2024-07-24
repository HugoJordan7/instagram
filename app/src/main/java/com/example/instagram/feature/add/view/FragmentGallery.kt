package com.example.instagram.feature.add.view

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.R

class FragmentGallery: Fragment() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_gallery,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.gallery_recycler_view)
        recyclerView.layoutManager = GridLayoutManager(requireContext(),3)
        recyclerView.adapter = PostAdapter(emptyList())
    }

    inner class PostAdapter(private val imageList: List<Int>): RecyclerView.Adapter<PostAdapter.PostViewHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
            val view = layoutInflater.inflate(R.layout.item_profile_grid,parent,false)
            return PostViewHolder(view)
        }

        override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
            holder.bind(R.drawable.ic_insta_add)
        }

        override fun getItemCount(): Int {
            return 30
        }

        inner class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            fun bind(image: Int){
                val imageView = itemView.findViewById<ImageView>(R.id.item_profile_img_grid)
                imageView.setImageResource(image)
            }
        }
    }

}