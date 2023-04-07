package com.example.testtaskarkanit.data.network.model

import com.example.testtaskarkanit.data.network.model.repo.ItemRepoResponse
import com.example.testtaskarkanit.data.network.model.repo.RepoResponse
import com.example.testtaskarkanit.data.network.model.user.ItemResponse
import com.example.testtaskarkanit.data.network.model.user.UserResponse
import com.example.testtaskarkanit.domain.model.repo.ItemRepo
import com.example.testtaskarkanit.domain.model.repo.Repo
import com.example.testtaskarkanit.domain.model.user.Item
import com.example.testtaskarkanit.domain.model.user.User

fun UserResponse.toDomain() = User(
    items = items?.map { it.toDomain() } ?: emptyList()
)

fun ItemResponse.toDomain() = Item(
    login = login ?: "Unknown",
    avatarUrl = avatarUrl ?: "https://http.cat/204", // Replace on image "code 204 error"
    htmlUrl = htmlUrl ?: "https://http.cat/404",
    score = score ?: 0.0
)

fun RepoResponse.toDomain() = Repo(
    items = items?.map { it.toDomain() } ?: emptyList()
)

fun ItemRepoResponse.toDomain() = ItemRepo(
    name = name ?: "unknown",
    description = description ?: "description is missing",
    forksCount = forksCount ?: 0,
    htmlUrl = htmlUrl ?: "https://http.cat/404"
)