package com.example.instagram.common.base

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.MenuRes
import androidx.fragment.app.Fragment

abstract class BaseFragment<B,P: BasePresenter>(
    @LayoutRes layoutId: Int,
    val bind: (View) -> B
): Fragment(layoutId) {

    protected abstract var presenter: P
    protected var binding: B? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(getMenu() != null){
            setHasOptionsMenu(true)
        }
        setupPresenter()
    }

    protected abstract fun setupPresenter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = bind(view)
        if (savedInstanceState == null){
            setupViews()
        }
    }

    protected abstract fun setupViews()

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
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
        presenter.onDestroy()
    }
}