package com.example.instagram.feature.profile.view

import android.annotation.SuppressLint
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagram.R
import com.example.instagram.common.base.BaseFragment
import com.example.instagram.common.model.Post
import com.example.instagram.common.model.UserAuth
import com.example.instagram.databinding.FragmentProfileBinding
import com.example.instagram.di.DependencyInjector
import com.example.instagram.feature.profile.Profile
import com.example.instagram.feature.profile.presentation.ProfilePresenter
import com.google.android.material.bottomnavigation.BottomNavigationView

class FragmentProfile : BaseFragment<FragmentProfileBinding, Profile.Presenter>(
    R.layout.fragment_profile,
    FragmentProfileBinding::bind
), Profile.View, BottomNavigationView.OnNavigationItemSelectedListener {

    override lateinit var presenter: Profile.Presenter

    private val adapter = PostAdapter()

    override fun setupPresenter() {
        val repository = DependencyInjector.profileRepository()
        presenter = ProfilePresenter(this, repository)
    }

    override fun setupViews() {
        binding?.profileRv?.layoutManager = GridLayoutManager(requireContext(), 3)
        binding?.profileRv?.adapter = adapter
        binding?.profileBottomNav?.setOnNavigationItemSelectedListener(this)
        presenter.fetchUserProfile()
    }

    override fun showProgress(enabled: Boolean) {
        binding?.profileProgressBar?.visibility = if (enabled) View.VISIBLE else View.GONE
    }

    override fun displayUserProfile(userAuth: UserAuth) {
        binding?.profileTxtCountPosts?.text = userAuth.postsCount.toString()
        binding?.profileTxtCountFollowing?.text = userAuth.followingCount.toString()
        binding?.profileTxtCountFollowers?.text = userAuth.followersCount.toString()
        binding?.profileTxtUsername?.text = userAuth.name
        binding?.profileTxtBio?.text = "TODO"
        binding?.profileImgIcon?.setImageURI(userAuth.photoUri)
        presenter.fetchUserPosts()
    }

    override fun displayFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun displayEmptyPosts() {
        binding?.profileTxtNoPosts?.visibility = View.VISIBLE
        binding?.profileRv?.visibility = View.GONE
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun displaysPosts(posts: List<Post>) {
        binding?.profileTxtNoPosts?.visibility = View.GONE
        binding?.profileRv?.visibility = View.VISIBLE
        adapter.posts = posts
        adapter.notifyDataSetChanged()
    }

    override fun getMenu(): Int {
        return R.menu.menu_profile
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_profile_grid -> {
                binding?.profileRv?.layoutManager = GridLayoutManager(requireContext(), 3)
            }
            R.id.menu_profile_listed -> {
                binding?.profileRv?.layoutManager = LinearLayoutManager(requireContext())
            }
            R.id.menu_profile_user -> {
                binding?.profileRv?.layoutManager = LinearLayoutManager(requireContext())
            }
        }
        return true
    }

}