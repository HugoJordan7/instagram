package com.example.instagram.feature.home.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.*
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagram.R
import com.example.instagram.common.base.BaseFragmentMVVM
import com.example.instagram.common.model.Post
import com.example.instagram.databinding.FragmentHomeBinding
import com.example.instagram.feature.home.presentation.HomeViewModel
import com.example.instagram.home.Home
import com.example.instagram.feature.main.LogoutListener

class FragmentHome: BaseFragmentMVVM<FragmentHomeBinding, HomeViewModel>(
    R.layout.fragment_home,
    FragmentHomeBinding::bind
){

    private val adapter = FeedAdapter()

    private var logoutListener: LogoutListener? = null

    //TODO: implement di
    override lateinit var viewModel: HomeViewModel

    override fun setupViews() {
        binding?.homeRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
        binding?.homeRecyclerView?.adapter = adapter
        viewModel.fetchFeed()

        viewModel.isLoading.observe(this){ isLoading ->
            showProgress(isLoading)
        }

        viewModel.isFailure.observe(this){ isFailure ->
            isFailure?.let { displayRequestFailure(it) }
        }

        viewModel.posts.observe(this){ posts ->
            if (posts.isEmpty()) displayEmptyPosts()
            else displayFullPosts(posts)
        }

    }

    override fun getMenu() = R.menu.menu_profile

    private fun showProgress(enabled: Boolean) {
        binding?.homeProgress?.visibility = if (enabled) View.VISIBLE else View.GONE
    }

    private fun displayRequestFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun displayEmptyPosts() {
        binding?.homeTxtNoPosts?.visibility = View.VISIBLE
        binding?.homeProgress?.visibility = View.GONE
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun displayFullPosts(posts: List<Post>) {
        binding?.homeTxtNoPosts?.visibility = View.GONE
        binding?.homeProgress?.visibility = View.VISIBLE
        adapter.items = posts
        adapter.notifyDataSetChanged()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is LogoutListener){
            logoutListener = context
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_logout -> logoutListener?.logout()
        }
        return super.onOptionsItemSelected(item)
    }

}