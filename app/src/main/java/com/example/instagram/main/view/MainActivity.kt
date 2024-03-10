package com.example.instagram.main.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
    private lateinit var fragmentSavedState: HashMap<String, Fragment.SavedState?>

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

        if (savedInstanceState == null) {
            fragmentSavedState = HashMap()
        } else {
            savedInstanceState.getSerializable(FRAGMENT_STATE) as HashMap<String, Fragment.SavedState>
        }

        binding.mainBottomNav.setOnNavigationItemSelectedListener(this)
        binding.mainBottomNav.selectedItemId = R.id.menu_bottom_home

    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(FRAGMENT_STATE, fragmentSavedState)
        super.onSaveInstanceState(outState)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val isScrollEnabled = false
        val selectedFragment: Fragment? = when (item.itemId) {
            R.id.menu_bottom_home -> {
                FragmentHome()
            }
            R.id.menu_bottom_profile -> {
                FragmentProfile()
            }
            else -> null
        }

        val currentFrag = supportFragmentManager.findFragmentById(R.id.main_fragment)
        val currentFragTag = currentFrag?.javaClass?.simpleName
        val selectedFragTag = selectedFragment?.javaClass?.simpleName

        currentFrag?.let { current ->
            Log.i("verifyTags","currentTag: $currentFragTag")
            Log.i("verifyTags", "selectedTag: $selectedFragTag")
            if (!currentFragTag.equals(selectedFragTag)) {
                fragmentSavedState.put(
                    currentFragTag!!,
                    supportFragmentManager.saveFragmentInstanceState(current)
                )
            }
        }

        selectedFragment?.let {
            it.setInitialSavedState(fragmentSavedState[selectedFragTag])
            replaceFragment(R.id.main_fragment, it, true, selectedFragTag)
        }

        setScrollToolbar(isScrollEnabled)
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

    companion object {
        const val FRAGMENT_STATE = "fragmentState"
    }

}