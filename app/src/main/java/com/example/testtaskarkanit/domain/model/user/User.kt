package com.example.testtaskarkanit.domain.model.user

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class User(
    val items: List<Item>
) : Parcelable