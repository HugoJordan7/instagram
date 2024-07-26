package com.example.instagram.feature.search.view

import android.app.SearchManager
import android.content.Context
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagram.R
import com.example.instagram.common.base.BaseFragment
import com.example.instagram.databinding.FragmentSearchBinding
import com.example.instagram.feature.search.Search
import com.example.instagram.feature.search.presentation.SearchPresenter

class FragmentSearch: BaseFragment<FragmentSearchBinding, Search.Presenter>(
    R.layout.fragment_search,
    FragmentSearchBinding::bind
), Search.View {

    override lateinit var presenter: Search.Presenter

    override fun setupPresenter() {
        presenter = SearchPresenter(this)
    }

    override fun setupViews() {
        binding?.searchRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
        binding?.searchRecyclerView?.adapter = PostAdapter()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        val searchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.menu_search).actionView as SearchView

        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
            setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean = false
                override fun onQueryTextChange(newText: String?): Boolean = false
            })
        }
    }

    override fun getMenu(): Int = R.menu.menu_search

}