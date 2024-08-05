package com.example.instagram.feature.profile.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagram.R
import com.example.instagram.common.base.BaseFragment
import com.example.instagram.common.di.DependencyInjector
import com.example.instagram.common.model.Post
import com.example.instagram.common.model.User
import com.example.instagram.common.util.KEY_USER_ID
import com.example.instagram.databinding.FragmentProfileBinding
import com.example.instagram.feature.main.LogoutListener
import com.example.instagram.feature.profile.Profile
import com.example.instagram.feature.profile.presentation.ProfilePresenter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso

class FragmentProfile : BaseFragment<FragmentProfileBinding, Profile.Presenter>(
    R.layout.fragment_profile,
    FragmentProfileBinding::bind
), Profile.View, BottomNavigationView.OnNavigationItemSelectedListener {

    private val adapter = PostAdapter()
    private var uuid: String? = null

    override lateinit var presenter: Profile.Presenter

    private var logoutListener: LogoutListener? = null

    override fun setupPresenter() {
        val repository = DependencyInjector.profileRepository()
        presenter = ProfilePresenter(this, repository)
    }

    override fun setupViews() {
        uuid = arguments?.getString(KEY_USER_ID)
        binding?.profileRv?.layoutManager = GridLayoutManager(requireContext(), 3)
        binding?.profileRv?.adapter = adapter
        binding?.profileBottomNav?.setOnNavigationItemSelectedListener(this)
        presenter.fetchUserProfile(uuid)

        binding?.profileButtonTop?.setOnClickListener{
            if(it.tag == true){
                binding?.profileButtonTop?.text = getString(R.string.follow)
                presenter.followUser(uuid, false)
                binding?.profileButtonTop?.tag = false
            } else if(it.tag == false){
                binding?.profileButtonTop?.text = getString(R.string.unfollow)
                presenter.followUser(uuid, true)
                binding?.profileButtonTop?.tag = true
            }
        }
    }

    override fun showProgress(enabled: Boolean) {
        binding?.profileProgressBar?.visibility = if (enabled) View.VISIBLE else View.GONE
    }

    override fun displayUserProfile(response: Pair<User, Boolean?>) {
        val (userAuth, following) = response
        binding?.profileTxtCountPosts?.text = userAuth.postCount.toString()
        binding?.profileTxtCountFollowing?.text = userAuth.followingCount.toString()
        binding?.profileTxtCountFollowers?.text = userAuth.followersCount.toString()
        binding?.profileTxtUsername?.text = userAuth.name
        binding?.profileTxtBio?.text = getString(R.string.app_name)
        Picasso.get().load(userAuth.photoUrl).into(binding?.profileImgIcon)
        presenter.fetchUserPosts(uuid)

        binding?.profileButtonTop?.text = when(following){
            null -> getString(R.string.edit_profile)
            true -> getString(R.string.unfollow)
            false -> getString(R.string.follow)
        }

        binding?.profileButtonTop?.tag = following
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