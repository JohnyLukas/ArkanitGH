package com.example.testtaskarkanit.presentation.main

sealed class ThisFragmentUIState {
    object Loading : ThisFragmentUIState()
    object Success : ThisFragmentUIState()
    data class NetworkError(
        val title: String,
        val description: String,
        val retryAction: () -> Unit
    ) : ThisFragmentUIState()
}
