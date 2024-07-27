package com.example.instagram.common.extension

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


//This method adding fragment in supportFragmentManager when fragment not exist and replace fragment when exist
//To force replace fragment when he not exist in supportFragmentManager, write forceReplace as true
fun AppCompatActivity.replaceFragment(@IdRes id: Int, fragment: Fragment, fragmentTag: String? = null, forceReplace: Boolean = false) {
    supportFragmentManager.beginTransaction().apply {
        val tag = fragmentTag ?: fragment.javaClass.simpleName
        if (supportFragmentManager.findFragmentById(id) == null && !forceReplace) {
            add(id, fragment, tag)
        } else {
            replace(id, fragment, tag)
            addToBackStack(null)
        }
        commit()
    }
    hideKeyBoard()
}

fun Activity.hideKeyBoard() {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view: View? = currentFocus
    if (view == null) {
        view = View(this)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.animationEnd(callback: () -> Unit): AnimatorListenerAdapter {
    return object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
            callback.invoke()
        }
    }
}