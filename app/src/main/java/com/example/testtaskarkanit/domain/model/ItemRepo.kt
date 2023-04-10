package com.example.testtaskarkanit.domain.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class ItemRepo(
    val name: String,
    val description: String,
    val forksCount: Int,
    val htmlUrl: String
) : Parcelable