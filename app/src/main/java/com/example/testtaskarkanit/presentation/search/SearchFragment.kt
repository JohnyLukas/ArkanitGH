package com.example.testtaskarkanit.presentation.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testtaskarkanit.R
import com.example.testtaskarkanit.databinding.FragmentSearchBinding
import com.example.testtaskarkanit.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment(R.layout.fragment_search) {
    override val viewModel: SearchViewModel by viewModels()
    private val binding: FragmentSearchBinding by viewBinding()
    private val adapter = Adapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?): Unit = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager =
            LinearLayoutManager(root.context, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel
                    .handleErrorInput.collect { exception ->
                        exception?.let {
                            searchInput.error = it
                        }
                    }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.listItems.collect { listItems ->
                    adapter.submitList(listItems)
                }
            }
        }

        searchButton.setOnClickListener {
            viewModel.checkInputError(searchInput.text.toString())
            viewModel.cleanList()
        }
    }
}