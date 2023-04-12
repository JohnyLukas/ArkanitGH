package com.example.testtaskarkanit.presentation.search

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.view.postDelayed
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testtaskarkanit.R
import com.example.testtaskarkanit.databinding.FragmentSearchBinding
import com.example.testtaskarkanit.presentation.base.BaseFragment
import com.example.testtaskarkanit.presentation.main.UIStateHandler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment(R.layout.fragment_search) {
    override val viewModel: SearchViewModel by viewModels()
    private val binding: FragmentSearchBinding by viewBinding()
    private val searchAdapter = SearchAdapter { repo ->
        findNavController().navigate(
            SearchFragmentDirections.actionShowRepositoryContent(
                repo.ownerUI!!.login,
                repo.name
            )
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?): Unit = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        recyclerView.adapter = searchAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.handleErrorInput.collect { exception ->
                    searchInput.error = exception
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.errorNetwork.collect { exception ->
                    exception?.let { (activity as UIStateHandler).showError(it) }
                    if (exception == null) (activity as UIStateHandler).hideError(false)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.listItems.collect { listItems ->
                    binding.root.postDelayed(2500) {
                        searchAdapter.submitList(listItems)
                        progressBar.isVisible = false
                    }
                }
            }
        }

        searchButton.setOnClickListener {
            progressBar.isVisible = true
            viewModel.checkInputError(searchInput.text.toString())
            viewModel.cleanList()
        }
    }
}