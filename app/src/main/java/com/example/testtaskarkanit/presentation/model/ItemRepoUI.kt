package com.example.testtaskarkanit.presentation.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class ItemRepoUI(
    val name: String,
    val description: String,
    val forksCount: Int,
    val htmlUrl: String
) : ItemsUI(), Parcelable
