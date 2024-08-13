package com.example.instagram.feature.main.view

import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.WindowInsetsController
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.instagram.App
import com.example.instagram.R
import com.example.instagram.common.extension.replaceFragment
import com.example.instagram.common.util.KEY_USER_ID
import com.example.instagram.databinding.ActivityMainBinding
import com.example.instagram.feature.di.component.MainComponent
import com.example.instagram.feature.home.view.FragmentHome
import com.example.instagram.feature.main.LogoutListener
import com.example.instagram.feature.post.view.FragmentAdd
import com.example.instagram.feature.profile.view.FragmentProfile
import com.example.instagram.feature.profile.view.FragmentProfile.FollowListener
import com.example.instagram.feature.search.view.FragmentSearch
import com.example.instagram.feature.splash.view.SplashActivity
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity :
    AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener,
    FragmentAdd.AddListener,
    FragmentSearch.SearchListener,
    LogoutListener,
    FollowListener
{

    private lateinit var homeFragment: FragmentHome
    private lateinit var searchFragment: Fragment
    private lateinit var addFragment: Fragment
    private lateinit var profileFragment: FragmentProfile
    private var currentFragment: Fragment? = null
    private lateinit var binding: ActivityMainBinding

    lateinit var mainComponent: MainComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainComponent = (applicationContext as App).applicationComponent.mainComponent().create()
        mainComponent.inject(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            when(resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)){
                Configuration.UI_MODE_NIGHT_YES ->{
                    window.statusBarColor = ContextCompat.getColor(this, R.color.black)
                }
                Configuration.UI_MODE_NIGHT_NO ->{
                    val theme = WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                    window.insetsController?.setSystemBarsAppearance(theme, theme)
                    window.statusBarColor = ContextCompat.getColor(this, R.color.gray)
                }
            }
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
        addFragment = FragmentAdd()
        profileFragment = FragmentProfile()

        binding.mainBottomNav.setOnNavigationItemSelectedListener(this)
        binding.mainBottomNav.selectedItemId = R.id.menu_bottom_home
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var isScrollEnabled = false
        currentFragment = when (item.itemId) {
            R.id.menu_bottom_home -> {
                if (currentFragment == homeFragment) return false
                isScrollEnabled = true
                homeFragment
            }
            R.id.menu_bottom_profile -> {
                if (currentFragment == profileFragment) return false
                isScrollEnabled = true
                profileFragment
            }
            R.id.menu_bottom_add -> {
                if (currentFragment == addFragment) return false
                isScrollEnabled = false
                addFragment
            }
            R.id.menu_bottom_search -> {
                if (currentFragment == searchFragment) return false
                isScrollEnabled = false
                searchFragment
            }
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

    override fun onPostCreated() {
        clearCache()
        binding.mainBottomNav.selectedItemId = R.id.menu_bottom_home
    }

    override fun goToProfileScreen(userUUID: String) {
        Log.i("searchTest", userUUID)
        val fragment = FragmentProfile()
        val bundle = Bundle().apply { putString(KEY_USER_ID, userUUID) }
        fragment.arguments = bundle
        replaceFragment(R.id.main_fragment, fragment, fragment.javaClass.simpleName + "detail", true)
    }

    override fun logout() {
        clearCache()
        homeFragment.viewModel.logout()
        val intent = Intent(this, SplashActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    private fun clearCache(){
        homeFragment.viewModel.clear()
        if(supportFragmentManager.findFragmentByTag(profileFragment.javaClass.simpleName) != null){
            profileFragment.presenter.clear()
        }
    }

    override fun followUpdated() {
        clearCache()
    }

}