package com.example.testtaskarkanit.presentation.base

import androidx.lifecycle.ViewModel
import com.example.testtaskarkanit.data.network.common.NetworkException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor() : ViewModel() {
    protected val _handlerError: MutableStateFlow<NetworkException?> = MutableStateFlow(null)
    val handleError: StateFlow<NetworkException?> = _handlerError.asStateFlow()
}