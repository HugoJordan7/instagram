package com.example.instagram.feature.add.view

import com.example.instagram.R
import com.example.instagram.common.base.BaseFragment
import com.example.instagram.databinding.FragmentAddBinding
import com.example.instagram.feature.add.Add
import com.google.android.material.tabs.TabLayoutMediator

class FragmentAdd: BaseFragment<FragmentAddBinding, Add.Presenter>(
    R.layout.fragment_add,
    FragmentAddBinding::bind
), Add.View {

    override lateinit var presenter: Add.Presenter

    override fun setupPresenter() {

    }

    override fun setupViews() {
        val viewPager = binding?.addViewPager
        val tabLayout = binding?.addTabLayout
        val adapter = AddViewPagerAdapter(requireActivity())
        viewPager?.adapter = adapter

        if(viewPager != null && tabLayout != null){
            TabLayoutMediator(tabLayout, viewPager){ tab, position ->
                tab.text = getString(adapter.tabs[position])
            }.attach()
        }
    }


}