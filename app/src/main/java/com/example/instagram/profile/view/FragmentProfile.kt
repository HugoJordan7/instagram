package com.example.instagram.profile.view

import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.example.instagram.R
import com.example.instagram.common.base.BaseFragment
import com.example.instagram.common.base.DependencyInjector
import com.example.instagram.common.model.Post
import com.example.instagram.common.model.UserAuth
import com.example.instagram.databinding.FragmentProfileBinding
import com.example.instagram.profile.ProfileContract
import com.example.instagram.profile.data.FakeProfileDataSource
import com.example.instagram.profile.data.ProfileRepository
import com.example.instagram.profile.presentation.ProfilePresenter

class FragmentProfile :
    BaseFragment<FragmentProfileBinding,ProfileContract.Presenter>(
        R.layout.fragment_profile,
        FragmentProfileBinding::bind
    ), ProfileContract.View {

    override lateinit var presenter: ProfileContract.Presenter
    private val adapter = PostAdapter()

    override fun setupPresenter() {
        val repository = DependencyInjector.profileRepository()
        presenter = ProfilePresenter(this,repository)
    }

    override fun setupViews() {
        binding?.profileRv?.layoutManager = GridLayoutManager(requireContext(), 3)
        binding?.profileRv?.adapter = adapter
        presenter.fetchUserProfile()
    }

    override fun getMenu() = R.menu.menu_main_toolbar

    override fun showProgress(enabled: Boolean) {
        binding?.profileProgressBar?.visibility = if(enabled) View.VISIBLE else View.GONE
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
        Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()
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

}