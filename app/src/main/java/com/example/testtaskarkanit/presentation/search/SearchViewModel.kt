package com.example.testtaskarkanit.presentation.search

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewModelScope
import com.example.testtaskarkanit.data.network.common.NetworkException
import com.example.testtaskarkanit.domain.model.repo.Repo
import com.example.testtaskarkanit.domain.model.user.User
import com.example.testtaskarkanit.domain.model.toUI
import com.example.testtaskarkanit.domain.usecase.GetListReposUseCase
import com.example.testtaskarkanit.domain.usecase.GetListReposUseCaseParam
import com.example.testtaskarkanit.domain.usecase.GetListUsersUseCase
import com.example.testtaskarkanit.domain.usecase.GetListUsersUseCaseParam
import com.example.testtaskarkanit.presentation.base.BaseViewModel
import com.example.testtaskarkanit.presentation.model.ItemsUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getListUsersUseCase: GetListUsersUseCase,
    private val getListReposUseCase: GetListReposUseCase
) : BaseViewModel() {
    private val _handleErrorInput: MutableStateFlow<String?> = MutableStateFlow(null)
    val handleErrorInput: StateFlow<String?> = _handleErrorInput.asStateFlow()

    private val _listItems: MutableStateFlow<List<ItemsUI>?> = MutableStateFlow(null)
    val listItems: StateFlow<List<ItemsUI>?> = _listItems.asStateFlow()

    fun checkInputError(requestText: String?) {
        when {
            requestText == null -> _handleErrorInput.value = "Please enter request"
            requestText.isDigitsOnly() -> _handleErrorInput.value = "Please check your input"
            requestText.length < 3 -> _handleErrorInput.value =
                "Please enter more than 3 characters"
            requestText.length > 16 -> _handleErrorInput.value = "Too many characters entered"
            else -> {
                mergeFLow(requestText)
            }
        }
    }

    fun cleanList() {
        _listItems.value = null
    }

    private fun mergeFLow(request: String) {
        viewModelScope.launch {
            getListUsersUseCase.invoke(
                param = GetListUsersUseCaseParam(request = request)
            ).combine(
                getListReposUseCase.invoke(
                    param = GetListReposUseCaseParam(request = request)
                ),
                transform = { it: Result<User>, it2: Result<Repo> ->
                    it to it2
                }
            ).collect { result: Pair<Result<User>, Result<Repo>> ->
                result.first.onSuccess { users ->
                    _listItems.value = _listItems.value.orEmpty() + users.items.map { it.toUI() }
                }.onFailure { throwable ->
                    _handlerError.value = throwable.localizedMessage?.let {
                        NetworkException(
                            description = throwable.message.toString(),
                        )
                    }
                }
                result.second.onSuccess { repos ->
                    _listItems.value = _listItems.value.orEmpty() + repos.items.map { it.toUI() }
                }.onFailure { throwable ->
                    _handlerError.value = throwable.localizedMessage?.let {
                        NetworkException(
                            description = throwable.message.toString()
                        )
                    }
                }
            }
        }
    }

}