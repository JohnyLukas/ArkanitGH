package com.example.testtaskarkanit.presentation.model.repoContent

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class RepoContentItemUI(
    val name: String,
    val type: RepoContentItemType,
    val htmlUrl: String
) : Parcelable

enum class RepoContentItemType {
    DIRECTORY, FILE;

    companion object {
        fun parseRepoContentType(type: String) : RepoContentItemType = when (type) {
            "dir" -> DIRECTORY
            "file" -> FILE
            else -> throw IllegalStateException()
        }
    }
}