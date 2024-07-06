package com.example.instagram.feature.main.view

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowInsetsController
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.instagram.R
import com.example.instagram.feature.camera.view.FragmentCamera
import com.example.instagram.common.extension.replaceFragment
import com.example.instagram.databinding.ActivityMainBinding
import com.example.instagram.feature.home.view.FragmentHome
import com.example.instagram.feature.profile.view.FragmentProfile
import com.example.instagram.feature.search.view.FragmentSearch
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private val homeFragment: Fragment = FragmentHome()
    private val searchFragment: Fragment = FragmentSearch()
    private val cameraFragment: Fragment = FragmentCamera()
    private val profileFragment: Fragment = FragmentProfile()
    private var currentFragment: Fragment? = null
    private lateinit var binding: ActivityMainBinding

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
        binding.mainBottomNav.setOnNavigationItemSelectedListener(this)
        binding.mainBottomNav.selectedItemId = R.id.menu_bottom_home
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var isScrollEnabled = false
        currentFragment = when (item.itemId) {
            R.id.menu_bottom_home -> {
                isScrollEnabled = true
                homeFragment
            }
            R.id.menu_bottom_profile -> {
                isScrollEnabled = true
                profileFragment
            }
            R.id.menu_bottom_add -> cameraFragment
            R.id.menu_bottom_search -> searchFragment
            else -> null
        }
        currentFragment?.let {
            replaceFragment(R.id.main_fragment, it)
        }
        setScrollToolbar(isScrollEnabled)
        return true
    }

    private fun setScrollToolbar(enabled: Boolean) {
        val appBarParams = binding.mainToolbar.layoutParams as AppBarLayout.LayoutParams
        val coordinatorParams = binding.mainAppBarLayout.layoutParams as CoordinatorLayout.LayoutParams
        if (enabled) {
            appBarParams.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
            coordinatorParams.behavior = AppBarLayout.Behavior()
        } else {
            appBarParams.scrollFlags = 0
            coordinatorParams.behavior = null
        }
        binding.mainAppBarLayout.layoutParams = coordinatorParams
    }

}