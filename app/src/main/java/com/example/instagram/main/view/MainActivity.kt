package com.example.instagram.main.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowInsetsController
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.instagram.R
import com.example.instagram.camera.view.FragmentCamera
import com.example.instagram.common.extension.replaceFragment
import com.example.instagram.databinding.ActivityMainBinding
import com.example.instagram.home.view.FragmentHome
import com.example.instagram.profile.view.FragmentProfile
import com.example.instagram.search.view.FragmentSearch
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var homeFragment: Fragment
    private lateinit var searchFragment: Fragment
    private lateinit var cameraFragment: Fragment
    private lateinit var favoritesFragment: Fragment
    private lateinit var profileFragment: Fragment
    private lateinit var currentFragment: Fragment
    private lateinit var selectedFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val theme = WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            window.insetsController?.setSystemBarsAppearance(theme, theme)
            window.statusBarColor = ContextCompat.getColor(this, R.color.gray)
        }
        val toolbar = findViewById<Toolbar>(R.id.main_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setHomeAsUpIndicator(R.drawable.ic_insta_camera)
            setDisplayHomeAsUpEnabled(true)
            title = ""
        }

        homeFragment = FragmentHome()
        searchFragment = FragmentSearch()
        cameraFragment = FragmentCamera()
        profileFragment = FragmentProfile()

        currentFragment = homeFragment

        binding.mainBottomNav.setOnNavigationItemSelectedListener(this)
        binding.mainBottomNav.selectedItemId = R.id.menu_bottom_home

        supportFragmentManager.beginTransaction().apply {
            add(R.id.main_fragment, homeFragment, "0")
            add(R.id.main_fragment, searchFragment, "1").hide(searchFragment)
            add(R.id.main_fragment, cameraFragment, "2").hide(cameraFragment)
            add(R.id.main_fragment, profileFragment, "3").hide(profileFragment)
            commit()
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var scrollToolbarEnabled = false
        when (item.itemId) {
            R.id.menu_bottom_home -> {
                selectedFragment = homeFragment
            }
            R.id.menu_bottom_search -> {
                selectedFragment = searchFragment
            }
            R.id.menu_bottom_add -> {
                selectedFragment = cameraFragment
            }
            R.id.menu_bottom_fav -> {
                //selectedFragment = homeFragment
            }
            R.id.menu_bottom_profile -> {
                selectedFragment = profileFragment
                scrollToolbarEnabled = true
            }
        }
        if (currentFragment == selectedFragment) return false

        supportFragmentManager.beginTransaction().apply {
            hide(currentFragment)
            show(selectedFragment)
            commit()
        }
        setScrollToolbar(scrollToolbarEnabled)
        currentFragment = selectedFragment
        //replaceFragment(R.id.main_fragment, currentFragment)
        return true
    }

    private fun setScrollToolbar(enabled: Boolean) {
        val appBarParams = binding.mainToolbar.layoutParams as AppBarLayout.LayoutParams
        val coordinatorParams =
            binding.mainAppBarLayout.layoutParams as CoordinatorLayout.LayoutParams
        if (enabled) {
            appBarParams.scrollFlags =
                AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
            coordinatorParams.behavior = AppBarLayout.Behavior()
        } else {
            appBarParams.scrollFlags = 0
            coordinatorParams.behavior = null
        }
        binding.mainAppBarLayout.layoutParams = coordinatorParams
    }

}