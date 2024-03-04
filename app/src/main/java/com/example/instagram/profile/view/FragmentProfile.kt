package com.example.instagram.profile.view

import androidx.recyclerview.widget.GridLayoutManager
import com.example.instagram.R
import com.example.instagram.common.base.BaseFragment
import com.example.instagram.databinding.FragmentProfileBinding
import com.example.instagram.profile.ProfileContract
import com.example.instagram.profile.presentation.ProfilePresenter

class FragmentProfile :
    BaseFragment<FragmentProfileBinding,ProfileContract.Presenter>(
        R.layout.fragment_profile,
        FragmentProfileBinding::bind
    ), ProfileContract.View {

    override lateinit var presenter: ProfileContract.Presenter

    override fun setupPresenter() {
        presenter = ProfilePresenter(this)
    }

    override fun setupViews() {
        binding?.profileRv?.layoutManager = GridLayoutManager(requireContext(), 3)
        binding?.profileRv?.adapter = PostAdapter()
    }

    override fun getMenu() = R.menu.menu_main_toolbar

    override fun showProgress(enabled: Boolean) {


    }

}