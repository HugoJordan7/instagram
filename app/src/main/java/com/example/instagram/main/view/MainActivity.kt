package com.example.instagram.main.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowInsetsController
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.instagram.R
import com.example.instagram.camera.view.FragmentCamera
import com.example.instagram.common.extension.replaceFragment
import com.example.instagram.databinding.ActivityMainBinding
import com.example.instagram.home.view.FragmentHome
import com.example.instagram.search.view.FragmentSearch
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var homeFragment: Fragment
    private lateinit var searchFragment: Fragment
    private lateinit var cameraFragment: Fragment
    private lateinit var favoritesFragment: Fragment
    private lateinit var profileFragment: Fragment
    private var currentFragment: Fragment? = null

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

        binding.mainBottomNav.setOnNavigationItemSelectedListener(this)
        binding.mainBottomNav.selectedItemId = R.id.menu_bottom_home

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragmentSelected: Fragment = when (item.itemId) {
            R.id.menu_bottom_home -> {
                homeFragment
            }
            R.id.menu_bottom_search -> {
                searchFragment
            }
            R.id.menu_bottom_add -> {
                cameraFragment
            }
            R.id.menu_bottom_fav -> {
                favoritesFragment
            }
            R.id.menu_bottom_profile -> {
                profileFragment
            }
            else -> profileFragment
        }

        return if (currentFragment == fragmentSelected) false else{
            currentFragment = fragmentSelected
            currentFragment?.let {
                replaceFragment(R.id.main_fragment, it)
            }
            true
        }

    }

}