package com.example.testtaskarkanit.data.network.model.user

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class UserResponse(
    @Json(name = "items")
    val items: List<ItemUserResponse>? = null
) : Parcelable