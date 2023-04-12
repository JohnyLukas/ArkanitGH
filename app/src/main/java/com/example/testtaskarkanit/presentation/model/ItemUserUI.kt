package com.example.testtaskarkanit.presentation.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class ItemUserUI(
    val login: String,
    val avatarUrl: String,
    val htmlUrl: String,
    val score: Double
) : ItemsUI(), Parcelable
