package com.example.testtaskarkanit.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.core.view.postDelayed
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testtaskarkanit.R
import com.example.testtaskarkanit.data.network.common.NetworkException
import com.example.testtaskarkanit.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), UIStateHandler {
    private val binding: ActivityMainBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun showError(error: NetworkException) = with(binding) {
        errorMessageText.text = error.title
        errorRoot.isVisible = true
        retryButton.setOnClickListener {
            refreshProgressBar.isVisible = true
            error.retryAction.invoke()
            root.postDelayed(2000) {
                refreshProgressBar.isVisible = false
            }
        }
    }

    override fun hideError(visibility: Boolean) {
        binding.errorRoot.isVisible = visibility
    }

    override fun showProgress() {
        TODO("Not yet implemented")
    }

    override fun hideProgress() {
        TODO("Not yet implemented")
    }
}