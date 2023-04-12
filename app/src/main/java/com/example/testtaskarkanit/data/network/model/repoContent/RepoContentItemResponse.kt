package com.example.testtaskarkanit.data.network.model.repoContent

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class RepoContentItemResponse(
    @Json(name = "name")
    val name: String? = null,
    @Json(name = "type")
    val type: String? = null,
    @Json(name = "html_url")
    val htmlUrl: String? = null
) : Parcelable