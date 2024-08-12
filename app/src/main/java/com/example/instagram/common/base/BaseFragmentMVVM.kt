package com.example.instagram.common.base

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.MenuRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.example.instagram.R

abstract class BaseFragmentMVVM<B, VM: ViewModel>(
    @LayoutRes layoutId: Int,
    val bind: (View) -> B
): Fragment(layoutId) {

    abstract var viewModel: VM
    protected var binding: B? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(getMenu() != null){
            setHasOptionsMenu(true)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = bind(view)
        setupViews()
    }

    protected abstract fun setupViews()

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.removeItem(R.id.action_send)
        getMenu()?.let {
            inflater.inflate(it, menu)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    @MenuRes
    protected open fun getMenu(): Int?{
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}