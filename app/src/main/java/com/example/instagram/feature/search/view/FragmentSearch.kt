package com.example.instagram.feature.search.view

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagram.R
import com.example.instagram.common.base.BaseFragment
import com.example.instagram.common.model.User
import com.example.instagram.databinding.FragmentSearchBinding
import com.example.instagram.feature.main.view.MainActivity
import com.example.instagram.feature.search.presentation.SearchViewModel
import javax.inject.Inject

class FragmentSearch: BaseFragment<FragmentSearchBinding, SearchViewModel>(
    R.layout.fragment_search,
    FragmentSearchBinding::bind
) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    override val viewModel by viewModels<SearchViewModel> { viewModelFactory }

    private val adapter by lazy { UserAdapter(onUserClickListener) }
    private var searchListener: SearchListener? = null

    private val onUserClickListener: (String) -> Unit = { userUUID ->
        searchListener?.goToProfileScreen(userUUID)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is SearchListener){
            searchListener = context
        }
        if(context is MainActivity){
            context.mainComponent.inject(this)
        }
    }

    override fun setupViews() {
        binding?.searchRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
        binding?.searchRecyclerView?.adapter = adapter

        viewModel.isFailure.observe(this){ isFailure ->
            isFailure?.let{ displayFailure(it) }
        }

        viewModel.isLoading.observe(this){ isLoading ->
            showProgress(isLoading)
        }

        viewModel.users.observe(this){ users ->
            users?.let {
                if (it.isEmpty()) displayEmptyUsers()
                else displayUsers(it)
            }
        }

    }

    private fun showProgress(enabled: Boolean) {
        binding?.searchProgressBar?.visibility = if(enabled) View.VISIBLE else View.GONE
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun displayUsers(users: List<User>) {
        binding?.searchTxtNoUsers?.visibility = View.GONE
        binding?.searchRecyclerView?.visibility = View.VISIBLE
        adapter.items = users
        adapter.notifyDataSetChanged()
    }

    private fun displayEmptyUsers() {
        binding?.searchTxtNoUsers?.visibility = View.VISIBLE
        binding?.searchRecyclerView?.visibility = View.GONE
    }

    private fun displayFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        val searchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.menu_search).actionView as SearchView

        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
            setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean = false
                override fun onQueryTextChange(newText: String?): Boolean {
                    if(newText?.isNotEmpty() == true){
                        viewModel.fetchUsers(newText)
                        return true
                    }
                    return false
                }
            })
        }
    }

    override fun getMenu(): Int = R.menu.menu_search

    interface SearchListener{
        fun goToProfileScreen(userUUID: String)
    }

}