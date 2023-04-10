package com.example.testtaskarkanit.data.network.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class RepoResponse(
    @Json(name = "items")
    val items: List<ItemRepoResponse>? = null
) : Parcelable