package com.example.instagram.feature.home.view

import android.view.*
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagram.R
import com.example.instagram.common.base.BaseFragment
import com.example.instagram.common.model.Post
import com.example.instagram.databinding.FragmentHomeBinding
import com.example.instagram.di.DependencyInjector
import com.example.instagram.home.Home
import com.example.instagram.feature.home.presentation.HomePresenter

class FragmentHome: BaseFragment<FragmentHomeBinding, Home.Presenter>(
    R.layout.fragment_home,
    FragmentHomeBinding::bind
), Home.View {

    override lateinit var presenter: Home.Presenter
    private val adapter = FeedAdapter()

    override fun setupViews() {
        binding?.homeRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
        binding?.homeRecyclerView?.adapter = adapter
        presenter.fetchFeed()
    }

    override fun setupPresenter() {
        val repository = DependencyInjector.homeRepository()
        presenter = HomePresenter(this, repository)
    }

    override fun getMenu() = R.menu.menu_profile

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_profile_bottom_nav, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun showProgress(enabled: Boolean) {
        binding?.homeProgress?.visibility = if (enabled) View.VISIBLE else View.GONE
    }

    override fun displayRequestFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun displayEmptyPosts() {
        binding?.homeTxtNoPosts?.visibility = View.VISIBLE
        binding?.homeProgress?.visibility = View.GONE
    }

    override fun displayFullPosts(posts: List<Post>) {
        binding?.homeTxtNoPosts?.visibility = View.GONE
        binding?.homeProgress?.visibility = View.VISIBLE
        adapter.items = posts
        adapter.notifyDataSetChanged()
    }

}