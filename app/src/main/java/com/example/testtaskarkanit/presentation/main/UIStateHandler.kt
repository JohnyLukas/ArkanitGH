package com.example.testtaskarkanit.presentation.main

interface UIStateHandler {
    fun showError(error: ThisFragmentUIState.NetworkError)

    fun hideError(visibility: Boolean)

    fun showLoading(visibility: Boolean)

}