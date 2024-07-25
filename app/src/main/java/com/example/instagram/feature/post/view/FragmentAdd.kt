package com.example.instagram.feature.post.view

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.activity.result.contract.ActivityResultContracts.PickContact
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.example.instagram.R
import com.example.instagram.databinding.FragmentAddBinding
import com.example.instagram.feature.add.view.AddActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class FragmentAdd : Fragment(R.layout.fragment_add) {

    private var binding: FragmentAddBinding? = null
    private var addListener: AddListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener("takePhotoKey") { requestKey, bundle ->
            val uri = bundle.getParcelable<Uri>("uri")
            uri?.let {
                val intent = Intent(requireContext(), AddActivity::class.java)
                intent.putExtra("photoUri", uri)
                addActivityResult.launch(intent)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AddListener) {
            addListener = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddBinding.bind(view)
        if (savedInstanceState == null) setupViews()
    }

    private fun setupViews() {
        val tabLayout = binding?.addTabLayout
        val viewPager = binding?.addViewPager
        val adapter = AddViewPagerAdapter(requireActivity())
        viewPager?.adapter = adapter

        if (tabLayout != null && viewPager != null) {
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    if (tab?.text == getString(adapter.tabs[0])) {
                        startCamera()
                    }
                }
                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = getString(adapter.tabs[position])
            }.attach()
        }

        if (allPermissionsGranted()) {
            startCamera()
        } else {
            getPermission.launch(REQUIRED_PERMISSION)
        }
    }

    private fun startCamera() {
        setFragmentResult("cameraKey", bundleOf("startCamera" to true))
    }

    private val addActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                addListener?.onPostCreated()
            }
        }

    interface AddListener {
        fun onPostCreated()
    }

    private val getPermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { granted ->
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(requireContext(), R.string.permission_camera_denied, Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted(): Boolean{
        var allPermissionsGranted = true
        for (permission in REQUIRED_PERMISSION){
            if (ContextCompat.checkSelfPermission(requireContext(), permission) != PackageManager.PERMISSION_GRANTED){
                allPermissionsGranted = false
            }
        }
        return allPermissionsGranted
    }

    companion object {
        private val REQUIRED_PERMISSION = if (Build.VERSION.SDK_INT >= 33){
            arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_MEDIA_VIDEO)
        } else{
            arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

}