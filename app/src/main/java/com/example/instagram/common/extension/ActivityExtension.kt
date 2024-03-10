package com.example.instagram.common.extension

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.ViewPropertyAnimator
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.instagram.R

fun AppCompatActivity.addFragment(@IdRes id: Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction().apply {
        add(id, fragment)
        commit()
    }
    hideKeyBoard()
}

fun AppCompatActivity.replaceFragment(@IdRes id: Int, fragment: Fragment, toBackStack: Boolean = true, name: String? = null) {
    supportFragmentManager.beginTransaction().apply {
        replace(id, fragment)
        if (toBackStack) {
            addToBackStack(name)
        }
        commit()
    }
    hideKeyBoard()
}

fun Activity.hideKeyBoard() {
    val imm: InputMethodManager =
        getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view: View? = currentFocus
    if (view == null) {
        view = View(this)
    }
    imm.hideSoftInputFromWindow(view?.windowToken, 0)
}

fun Activity.animationEnd(callback: () -> Unit): AnimatorListenerAdapter {
    return object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator?) {
            callback.invoke()
        }
    }
}