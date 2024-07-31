package com.example.instagram.feature.post.view

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.R
import com.example.instagram.common.base.BaseFragment
import com.example.instagram.common.util.TAKE_PHOTO_KEY
import com.example.instagram.common.util.URI
import com.example.instagram.databinding.FragmentGalleryBinding
import com.example.instagram.common.di.DependencyInjector
import com.example.instagram.feature.post.Post
import com.example.instagram.feature.post.presentation.PostPresenter

class FragmentGallery : BaseFragment<FragmentGalleryBinding, Post.Presenter>(
    R.layout.fragment_gallery,
    FragmentGalleryBinding::bind
), Post.View {

    override lateinit var presenter: Post.Presenter

    private val adapter = PictureAdapter { uri ->
        binding?.galleryImgSelected?.setImageURI(uri)
        binding?.galleryNestedScroll?.smoothScrollTo(0, 0)
        presenter.selectUri(uri)
    }

    override fun setupPresenter() {
        presenter = PostPresenter(this, DependencyInjector.postRepository(requireContext()))
    }

    override fun setupViews() {
        binding?.galleryRecyclerView?.layoutManager = GridLayoutManager(requireContext(), 3)
        binding?.galleryRecyclerView?.adapter = adapter
        presenter.fetchPictures()
    }

    override fun getMenu(): Int = R.menu.menu_send

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_send ->{
                setFragmentResult(TAKE_PHOTO_KEY, bundleOf(URI to presenter.getSelectedUri()))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showProgress(enabled: Boolean) {
        binding?.galleryProgressBar?.visibility = if (enabled) View.VISIBLE else View.GONE
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun displayPictures(posts: List<Uri>) {
        binding?.galleryTxtEmpty?.visibility = View.GONE
        binding?.galleryRecyclerView?.visibility = View.VISIBLE
        adapter.imageList = posts
        adapter.notifyDataSetChanged()
        binding?.galleryImgSelected?.setImageURI(posts.first())
        binding?.galleryNestedScroll?.smoothScrollTo(0, 0)
        presenter.selectUri(posts.first())
    }

    override fun displayEmptyPictures() {
        binding?.galleryTxtEmpty?.visibility = View.VISIBLE
        binding?.galleryRecyclerView?.visibility = View.GONE
    }

    override fun displayFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }
}