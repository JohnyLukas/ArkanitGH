package com.example.testtaskarkanit.domain.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Repo(
    @Json(name = "items")
    val items: List<ItemRepo>
) : Parcelable