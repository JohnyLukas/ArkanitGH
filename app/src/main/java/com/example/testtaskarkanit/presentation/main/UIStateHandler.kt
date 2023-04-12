package com.example.testtaskarkanit.presentation.main

import com.example.testtaskarkanit.data.network.common.NetworkException

interface UIStateHandler {
    fun showError(error: NetworkException){}

    fun hideError(visibility: Boolean) {}
}