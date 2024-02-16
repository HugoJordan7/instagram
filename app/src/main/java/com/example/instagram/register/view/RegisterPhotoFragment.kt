package com.example.instagram.register.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.instagram.R
import com.example.instagram.common.view.CustomDialog

class RegisterPhotoFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register_photo,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val customDialog = CustomDialog(requireContext())
        customDialog.addButton(R.string.gallery,R.string.photo){
            when(it.id){
                R.string.gallery -> {
                    Log.i("dialogTest","gallery")
                }
                R.string.photo -> {
                    Log.i("dialogTest","photo")
                }
            }
        }
        customDialog.show()
    }

}