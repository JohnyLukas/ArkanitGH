package com.example.testtaskarkanit.presentation.repositoryContent

import androidx.lifecycle.viewModelScope
import com.example.testtaskarkanit.R
import com.example.testtaskarkanit.data.network.common.NetworkException
import com.example.testtaskarkanit.domain.model.toUI
import com.example.testtaskarkanit.domain.usecase.GetRepoContentFlowUseCase
import com.example.testtaskarkanit.domain.usecase.GetRepoContentUseCaseParam
import com.example.testtaskarkanit.presentation.base.BaseViewModel
import com.example.testtaskarkanit.presentation.main.ThisFragmentUIState
import com.example.testtaskarkanit.presentation.model.repoContent.RepoContentItemUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContentViewModel @Inject constructor(
    private val getRepoContentUseCase: GetRepoContentFlowUseCase
) : BaseViewModel() {
    private val _errorNetwork: MutableStateFlow<NetworkException?> = MutableStateFlow(null)
    val errorNetwork: StateFlow<NetworkException?> = _errorNetwork.asStateFlow()

    private val _loadingUIState: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val loadingUIState: StateFlow<Boolean?> = _loadingUIState.asStateFlow()

    private val _listContent: MutableStateFlow<List<RepoContentItemUI>?> = MutableStateFlow(null)
    val listContent: StateFlow<List<RepoContentItemUI>?> = _listContent.asStateFlow()

    private val _fragmentUIState: MutableStateFlow<ThisFragmentUIState?> = MutableStateFlow(null)
    val fragmentUIState: StateFlow<ThisFragmentUIState?> = _fragmentUIState.asStateFlow()

    fun getListContent(owner: String, repo: String, path: String) {
        viewModelScope.launch {
            getRepoContentUseCase.invoke(
                param = GetRepoContentUseCaseParam(
                    owner, repo, path
                )
            ).onStart {
                /*_loadingUIState.value = true*/
                _fragmentUIState.value = ThisFragmentUIState.Loading
            }.onCompletion {
                /*_loadingUIState.value = false*/
                _fragmentUIState.value = ThisFragmentUIState.Success
            }.collect { result ->
                result.onSuccess { content ->
                    _listContent.value = content.map { it.toUI() }.sortedBy { it.type.ordinal }
                }.onFailure { throwable ->
                    /*_errorNetwork.value = NetworkException(
                        title = throwable.message ?: R.string.common_error_title.toString(),
                        description = throwable.toString(),
                        retryAction = {
                            getListContent(owner, repo, path)
                        }
                    )*/
                    _fragmentUIState.value = ThisFragmentUIState.NetworkError(
                        title = throwable.message ?: R.string.common_error_title.toString(),
                        description = throwable.toString(),
                        retryAction = { getListContent(owner, repo, path) }
                    )
                }
            }
        }
    }
}