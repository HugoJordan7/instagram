package com.example.instagram.feature.add.view

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.example.instagram.R
import com.example.instagram.common.base.BaseFragment
import com.example.instagram.databinding.FragmentAddBinding
import com.example.instagram.feature.add.Add
import com.google.android.material.tabs.TabLayout
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
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    if(tab?.text == getString(adapter.tabs[0])){
                        startCamera()
                    }
                }
                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
            TabLayoutMediator(tabLayout, viewPager){ tab, position ->
                tab.text = getString(adapter.tabs[position])
            }.attach()
        }
        if (allPermissionGranted()){
            startCamera()
        } else{
            getPermission.launch(REQUIRED_PERMISSION)
        }
    }

    private fun allPermissionGranted(): Boolean{
        return ContextCompat.checkSelfPermission(requireContext(), REQUIRED_PERMISSION) == PackageManager.PERMISSION_GRANTED
    }

    private fun startCamera(){
        setFragmentResult("cameraKey", bundleOf("startCamera" to true))
    }

    private val getPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()){ granted ->
        if (allPermissionGranted()){
            startCamera()
        } else{
            Toast.makeText(requireContext(), R.string.permission_camera_denied, Toast.LENGTH_LONG).show()
        }
    }

    companion object{
        const val REQUIRED_PERMISSION: String = Manifest.permission.CAMERA
    }

    private abstract class TabListener: TabLayout.OnTabSelectedListener{
        abstract override fun onTabSelected(tab: TabLayout.Tab?)
        override fun onTabUnselected(tab: TabLayout.Tab?) {}
        override fun onTabReselected(tab: TabLayout.Tab?) {}
    }

}