package com.example.testtaskarkanit.data.network.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class ItemRepoResponse(
    @Json(name = "name")
    val name: String? = null,
    @Json(name = "description")
    val description: String? = null,
    @Json(name = "forks_count")
    val forksCount: Int? = null,
    @Json(name = "html_url")
    val htmlUrl: String? = null
) : Parcelable