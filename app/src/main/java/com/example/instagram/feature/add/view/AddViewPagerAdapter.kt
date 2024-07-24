package com.example.instagram.feature.add.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.instagram.R
import java.lang.IllegalArgumentException

class AddViewPagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {

    val tabs = arrayOf(R.string.photo, R.string.gallery)

    override fun getItemCount(): Int = tabs.size

    override fun createFragment(position: Int): Fragment {
        return when(tabs[position]){
            R.string.photo -> FragmentCamera()
            R.string.gallery -> FragmentGallery()
            else -> throw IllegalArgumentException("Fragment not found")
        }
    }

}