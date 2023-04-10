package com.example.testtaskarkanit.presentation.search

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewModelScope
import com.example.testtaskarkanit.data.network.common.NetworkException
import com.example.testtaskarkanit.domain.model.toUI
import com.example.testtaskarkanit.domain.usecase.GetListReposUseCase
import com.example.testtaskarkanit.domain.usecase.GetListReposUseCaseParam
import com.example.testtaskarkanit.domain.usecase.GetListUsersUseCase
import com.example.testtaskarkanit.domain.usecase.GetListUsersUseCaseParam
import com.example.testtaskarkanit.presentation.base.BaseViewModel
import com.example.testtaskarkanit.presentation.model.ItemRepoUI
import com.example.testtaskarkanit.presentation.model.ItemUserUI
import com.example.testtaskarkanit.presentation.model.ItemsUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getListUsersUseCase: GetListUsersUseCase,
    private val getListReposUseCase: GetListReposUseCase
): BaseViewModel() {
    private val _handleErrorInput: MutableStateFlow<String?> = MutableStateFlow(null)
    val handleErrorInput: StateFlow<String?> = _handleErrorInput.asStateFlow()

    private val _listUsers: MutableStateFlow<List<ItemUserUI>?> = MutableStateFlow(null)
    private val _listRepos: MutableStateFlow<List<ItemRepoUI>?> = MutableStateFlow(null)

    private val _listItems: MutableStateFlow<List<ItemsUI>?> = MutableStateFlow(null)
    val listItems: StateFlow<List<ItemsUI>?> = _listItems.asStateFlow()

    fun checkInputError(requestText: String?) {
        when {
            requestText == null -> _handleErrorInput.value = "Please enter request"
            requestText.isDigitsOnly() -> _handleErrorInput.value = "Please check your input"
            requestText.length < 3 -> _handleErrorInput.value = "Please enter more than 3 characters"
            requestText.length > 16 -> _handleErrorInput.value = "Too many characters entered"
            else -> {
                getUsersList(requestText)
                getReposList(requestText)
                mergeFLow(_listUsers, _listRepos)
            }
        }
    }

    fun cleanList() {
        _listItems.value = null
    }

    private fun mergeFLow(
        flowA: MutableStateFlow<List<ItemUserUI>?>,
        flowB: MutableStateFlow<List<ItemRepoUI>?>
    ) {
        viewModelScope.launch {
            merge(flowA, flowB).collect { list ->
                list?.let {
                    _listItems.value = _listItems.value.orEmpty() + list
                }
            }
        }
    }

    private fun getUsersList(request: String) {
        viewModelScope.launch {
            getListUsersUseCase.invoke(
                param = GetListUsersUseCaseParam(request = request)
            ).collect { result ->
                result.onSuccess { users ->
                    _listUsers.value = users.items.map { it.toUI() }
                }.onFailure { throwable ->
                    _handlerError.value = throwable.localizedMessage?.let {
                        NetworkException(
                            title = throwable.message.toString(),
                            description = throwable.toString()
                        )
                    }
                }
            }
        }
    }

    private fun getReposList(request: String) {
        viewModelScope.launch {
            getListReposUseCase.invoke(
                param = GetListReposUseCaseParam(request = request)
            ).collect { result ->
                result.onSuccess { repos ->
                    _listRepos.value = repos.items.map { it.toUI() }
                }.onFailure { throwable ->
                    _handlerError.value = throwable.localizedMessage?.let {
                        NetworkException(
                            title = throwable.message.toString(),
                            description = throwable.toString()
                        )
                    }
                }
            }
        }
    }
}