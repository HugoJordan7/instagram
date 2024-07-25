package com.example.instagram.common.extension

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.replaceFragment(@IdRes id: Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction().apply {
        val tag = fragment.javaClass.simpleName
        if (supportFragmentManager.findFragmentById(id) == null) {
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
        override fun onAnimationEnd(animation: Animator?) {
            callback.invoke()
        }
    }
}