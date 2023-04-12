package com.example.testtaskarkanit.domain.model.repo

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Owner(
    val login: String
) : Parcelable
