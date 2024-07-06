package com.example.instagram.feature.profile.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.instagram.R
import com.example.instagram.common.base.BaseFragment
import com.example.instagram.di.DependencyInjector
import com.example.instagram.common.model.Post
import com.example.instagram.common.model.UserAuth
import com.example.instagram.databinding.FragmentProfileBinding
import com.example.instagram.feature.profile.ProfileContract
import com.example.instagram.feature.profile.presentation.ProfilePresenter

class FragmentProfile : BaseFragment<FragmentProfileBinding, ProfileContract.Presenter>(
    R.layout.fragment_profile,
    FragmentProfileBinding::bind
), ProfileContract.View {

    override lateinit var presenter: ProfileContract.Presenter
    private val adapter = PostAdapter()

    override fun setupPresenter() {
        val repository = DependencyInjector.profileRepository()
        presenter = ProfilePresenter(this, repository)
    }

    override fun setupViews() {
        binding?.profileRv?.layoutManager = GridLayoutManager(requireContext(), 3)
        binding?.profileRv?.adapter = adapter
        presenter.fetchUserProfile()
    }

    override fun getMenu() = R.menu.menu_main_toolbar

    override fun showProgress(enabled: Boolean) {
        binding?.profileProgressBar?.visibility = if (enabled) View.VISIBLE else View.GONE
    }

    override fun displayUserProfile(userAuth: UserAuth) {
        binding?.apply {
            profileTxtUsername.text = userAuth.name
            profileTxtCountPosts.text = userAuth.postsCount.toString()
            profileTxtCountFollowers.text = userAuth.followersCount.toString()
            profileTxtCountFollowing.text = userAuth.followingCount.toString()
            profileTxtBio.text = "TODO: After implement"
            presenter.fetchUserPosts()
        }
    }

    override fun displayFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun displayEmptyPosts() {
        binding?.profileTxtNoPosts?.visibility = View.VISIBLE
        binding?.profileProgressBar?.visibility = View.GONE
    }

    override fun displaysPosts(posts: List<Post>) {
        binding?.profileTxtNoPosts?.visibility = View.GONE
        binding?.profileProgressBar?.visibility = View.VISIBLE
        adapter.posts = posts
        adapter.notifyDataSetChanged()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        //outState.putParcelable(PROFILE_STATE, presenter.state)
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if(savedInstanceState != null) {
            val state = savedInstanceState.getParcelable<UserAuth?>(PROFILE_STATE)
            //presenter.state = state
            Log.i("saveState","${state?.name}")
            state?.let {
                displayUserProfile(it)
            }
        }
    }

    companion object {
        const val PROFILE_STATE = "profileState"
    }
}