package com.schools.nycschools.ui.home

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.schools.nycschools.R
import com.schools.nycschools.databinding.FragmentHomeBinding
import com.schools.nycschools.domain.model.NYCSchoolResponseItem
import com.schools.nycschools.domain.model.Output
import com.schools.nycschools.ui.base.BaseFragment
import com.schools.nycschools.utils.callClick
import com.schools.nycschools.utils.directionClick
import com.schools.nycschools.utils.emailClick
import com.schools.nycschools.utils.faxClick
import com.schools.nycschools.utils.hideKeyboard
import com.schools.nycschools.utils.linkClick
import com.schools.nycschools.utils.setOnSafeClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Home fragment
 *
 * @constructor Create empty Home fragment
 */
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var nycSchoolsAdapter: NycSchoolsAdapter
    private var nycSchoolResponseItem: List<NYCSchoolResponseItem> = emptyList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        initObservers()
        setHasOptionsMenu(true)
    }

    private fun initUi() {
        nycSchoolsAdapter = NycSchoolsAdapter(
            mutableListOf(),
            onCardClick = { nycSchool -> onCardClick(nycSchool) },
            callClick = { number -> requireActivity().callClick(number) },
            emailClick = { email -> requireActivity().emailClick(email) },
            faxClick = { number -> requireActivity().faxClick(number) },
            linkClick = { website -> requireActivity().linkClick(website) },
            directionLink = { lat, long -> requireActivity().directionClick(lat, long) }
        )

        binding.run {
            recyclerViewSchools.adapter = nycSchoolsAdapter
            recyclerViewSchools.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.run {
                    launch {
                        homePageState.collect {
                            when (it.status) {
                                Output.Status.LOADING -> {
                                    setViewVisibility(Output.Status.LOADING)
                                }

                                Output.Status.SUCCESS -> {
                                    it.data?.let { data ->
                                        nycSchoolResponseItem = data
                                        nycSchoolsAdapter.update(data)
                                    }
                                    setViewVisibility(Output.Status.SUCCESS)
                                }

                                Output.Status.ERROR -> {
                                    setViewVisibility(Output.Status.ERROR, it.error?.statusMessage)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setViewVisibility(status: Output.Status, error: String? = null) {
        binding.run {
            progressDialog.isVisible = status == Output.Status.LOADING
            layoutViews.isVisible = status == Output.Status.SUCCESS
            errorLayout.root.isVisible = status == Output.Status.ERROR
            errorLayout.btnTryAgain.setOnSafeClickListener {
                initObservers()
            }
            error?.let {
                errorLayout.labelErrorDescription.text = it
            }
        }
    }

    private fun onCardClick(nycSchool: NYCSchoolResponseItem) {
        findNavController().navigate(
            HomeFragmentDirections
                .actionHomeFragmentToSchoolDetailFragment(nycSchool = nycSchool)
        )
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem: MenuItem = menu.findItem(R.id.actionSearch)
        val searchManager =
            requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = searchItem.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                viewLifecycleOwner.lifecycleScope.launch {
                    requireActivity().hideKeyboard(searchView)
                    nycSchoolsAdapter.filter.filter(query)
                    binding.run {
                        recyclerViewSchools.visibility = View.VISIBLE
                        layoutViews.scrollTo(0, 0)
                    }
                }
                return false
            }
        })
        val closeBtn = searchView.findViewById<View>(androidx.appcompat.R.id.search_close_btn)
        closeBtn.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                searchView.setQuery("", false) // reset Query text to be empty without submition
                searchView.isIconified = true // Replace the x icon with the search icon
                searchView.onActionViewCollapsed()
                nycSchoolsAdapter.update(nycSchoolResponseItem)
                binding.run {
                    recyclerViewSchools.visibility = View.VISIBLE
                    layoutViews.scrollTo(0, 0)
                }
            }
        }
        super.onCreateOptionsMenu(menu, inflater)
    }
}
