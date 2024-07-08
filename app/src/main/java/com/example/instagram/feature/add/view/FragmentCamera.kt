package com.example.instagram.feature.add.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.instagram.R

class FragmentCamera: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_camera,container,false)
    }
}