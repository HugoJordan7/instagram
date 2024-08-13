package com.example.instagram.feature.di.component

import androidx.fragment.app.Fragment
import com.example.instagram.feature.di.module.MainModule
import com.example.instagram.feature.home.view.FragmentHome
import com.example.instagram.feature.main.view.MainActivity
import com.example.instagram.feature.post.view.FragmentAdd
import com.example.instagram.feature.post.view.FragmentGallery
import com.example.instagram.feature.profile.view.FragmentProfile
import com.example.instagram.feature.search.view.FragmentSearch
import dagger.Subcomponent

@Subcomponent(modules = [MainModule::class])
interface MainComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create(): MainComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: Fragment)
    fun inject(fragment: FragmentHome)
    fun inject(fragment: FragmentGallery)
    fun inject(fragment: FragmentSearch)
    fun inject(fragment: FragmentProfile)

}