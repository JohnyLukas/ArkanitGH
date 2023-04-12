package com.example.testtaskarkanit.domain.model.repoContent

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class RepoContentItem(
    val name: String,
    val type: String,
    val htmlUrl: String
) : Parcelable
