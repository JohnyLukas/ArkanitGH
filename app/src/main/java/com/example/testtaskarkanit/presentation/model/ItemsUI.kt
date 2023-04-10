package com.example.testtaskarkanit.presentation.model

sealed class ItemsUI {
    fun getItemId(): String {
        return when(this) {
            is ItemUserUI -> this.login
            is ItemRepoUI -> this.name
        }
    }

}
