package com.example.testtaskarkanit.presentation.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class RepoUI(
    val items: List<ItemRepoUI>
): Parcelable
