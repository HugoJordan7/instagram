package com.example.instagram.common.extension

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyBoard(){
    val imm: InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view: View? = currentFocus
    if(view == null){
        view = View(this)
    }
    imm.hideSoftInputFromWindow(view?.windowToken,0)
}