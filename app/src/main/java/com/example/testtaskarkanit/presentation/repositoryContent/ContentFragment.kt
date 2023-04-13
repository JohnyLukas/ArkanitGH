package com.example.testtaskarkanit.presentation.repositoryContent

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testtaskarkanit.R
import com.example.testtaskarkanit.databinding.RepositoryContentBinding
import com.example.testtaskarkanit.presentation.base.BaseFragment
import com.example.testtaskarkanit.presentation.main.ThisFragmentUIState
import com.example.testtaskarkanit.presentation.main.UIStateHandler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ContentFragment : BaseFragment(R.layout.repository_content) {
    override val viewModel: ContentViewModel by viewModels()
    private val binding: RepositoryContentBinding by viewBinding()
    private val args: ContentFragmentArgs by navArgs()
    private val listDirName: MutableList<String> = mutableListOf()
    val adapter = ContentAdapter { file ->
        explorer(file.name)
    }

    private fun explorer(dirName: String) {
        listDirName.add(dirName)
        viewModel.getListContent(
            owner = args.owner,
            repo = args.repoName,
            path = listDirName.joinToString(separator = "/")
        )
    }

    private fun onBack() {
        if (listDirName.isNotEmpty()) {
            listDirName.removeLast()
            viewModel.getListContent(
                owner = args.owner,
                repo = args.repoName,
                path = listDirName.joinToString(separator = "/")
            )
        } else {
            findNavController().popBackStack()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getListContent(args.owner, args.repoName, "")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?): Unit = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        repositoryContent.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        repositoryContent.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.listContent.collect { listItems ->
                    listItems?.let { adapter.submitList(it) }
                }
            }
        }

        /*viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.errorNetwork.collect { exception ->
                    exception?.let{ (activity as UIStateHandler).showError(it) }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.loadingUIState.collect { visibility ->
                    visibility?.let { (activity as UIStateHandler).showLoading(it) }
                }
            }
        }*/

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.fragmentUIState.collect { uiState ->
                    when (uiState) {
                        is ThisFragmentUIState.Loading -> {
                            (activity as UIStateHandler).showLoading(true)
                            (activity as UIStateHandler).hideError(false)
                        }
                        is ThisFragmentUIState.Success -> {
                            (activity as UIStateHandler).showLoading(false)
                            (activity as UIStateHandler).hideError(false)
                        }
                        is ThisFragmentUIState.NetworkError ->
                            (activity as UIStateHandler).showError(uiState)
                        else -> (activity as UIStateHandler).showError(
                            ThisFragmentUIState.NetworkError(
                                title = "Ops, something went wrong!",
                                description = "Unknown error",
                                retryAction = { viewModel.getListContent(args.owner, args.repoName, "") }
                            )
                        )
                    }
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    (activity as UIStateHandler).hideError(false)
                    onBack()
                }
            }
        )
    }
}