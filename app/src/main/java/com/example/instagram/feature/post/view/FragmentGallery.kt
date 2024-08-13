package com.example.instagram.feature.post.view

import android.annotation.SuppressLint
import android.net.Uri
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.GridLayoutManager
import com.example.instagram.R
import com.example.instagram.common.base.BaseFragmentMVVM
import com.example.instagram.common.util.TAKE_PHOTO_KEY
import com.example.instagram.common.util.URI
import com.example.instagram.databinding.FragmentGalleryBinding
import com.example.instagram.feature.post.presentation.PostViewModel

class FragmentGallery : BaseFragmentMVVM<FragmentGalleryBinding, PostViewModel>(
    R.layout.fragment_gallery,
    FragmentGalleryBinding::bind
){

    override lateinit var viewModel: PostViewModel

    private val adapter = PictureAdapter { uri ->
        binding?.galleryImgSelected?.setImageURI(uri)
        binding?.galleryNestedScroll?.smoothScrollTo(0, 0)
        viewModel.selectUri(uri)
    }


    override fun setupViews() {
        binding?.galleryRecyclerView?.layoutManager = GridLayoutManager(requireContext(), 3)
        binding?.galleryRecyclerView?.adapter = adapter
        viewModel.fetchPictures()
    }

    override fun getMenu(): Int = R.menu.menu_send

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_send ->{
                setFragmentResult(TAKE_PHOTO_KEY, bundleOf(URI to viewModel.getSelectedUri()))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showProgress(enabled: Boolean) {
        binding?.galleryProgressBar?.visibility = if (enabled) View.VISIBLE else View.GONE
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun displayPictures(posts: List<Uri>) {
        binding?.galleryTxtEmpty?.visibility = View.GONE
        binding?.galleryRecyclerView?.visibility = View.VISIBLE
        adapter.imageList = posts
        adapter.notifyDataSetChanged()
        binding?.galleryImgSelected?.setImageURI(posts.first())
        binding?.galleryNestedScroll?.smoothScrollTo(0, 0)
        viewModel.selectUri(posts.first())
    }

    private fun displayEmptyPictures() {
        binding?.galleryTxtEmpty?.visibility = View.VISIBLE
        binding?.galleryRecyclerView?.visibility = View.GONE
    }

    private fun displayFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

}