package com.example.testtaskarkanit.data.network.model.repo

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class OwnerResponse(
    @Json(name = "login")
    val login: String? = null
) : Parcelable
