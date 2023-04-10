package com.example.testtaskarkanit.data.network.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class ItemUserResponse(
    @Json(name = "login")
    val login: String? = null,
    @Json(name = "avatar_url")
    val avatarUrl: String? = null,
    @Json(name = "html_url")
    val htmlUrl: String? = null,
    @Json(name = "score")
    val score: Double? = null,
) : Parcelable