package com.example.testtaskarkanit.data.network.model

import com.example.testtaskarkanit.domain.model.ItemRepo
import com.example.testtaskarkanit.domain.model.Repo
import com.example.testtaskarkanit.domain.model.ItemUser
import com.example.testtaskarkanit.domain.model.User

fun UserResponse.toDomain() = User(
    items = items?.map { it.toDomain() } ?: emptyList()
)

fun ItemUserResponse.toDomain() = ItemUser(
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