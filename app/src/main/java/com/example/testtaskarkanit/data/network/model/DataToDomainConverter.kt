package com.example.testtaskarkanit.data.network.model

import com.example.testtaskarkanit.data.network.model.repo.ItemRepoResponse
import com.example.testtaskarkanit.data.network.model.repo.OwnerResponse
import com.example.testtaskarkanit.data.network.model.repo.RepoResponse
import com.example.testtaskarkanit.data.network.model.repoContent.RepoContentItemResponse
import com.example.testtaskarkanit.data.network.model.user.ItemUserResponse
import com.example.testtaskarkanit.data.network.model.user.UserResponse
import com.example.testtaskarkanit.domain.model.repo.ItemRepo
import com.example.testtaskarkanit.domain.model.repo.Owner
import com.example.testtaskarkanit.domain.model.repo.Repo
import com.example.testtaskarkanit.domain.model.repoContent.RepoContentItem
import com.example.testtaskarkanit.domain.model.user.ItemUser
import com.example.testtaskarkanit.domain.model.user.User

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
    htmlUrl = htmlUrl ?: "https://http.cat/404",
    owner = owner?.toDomain()
)

fun OwnerResponse.toDomain() = Owner(
    login = login ?: "mojobo"
)

fun RepoContentItemResponse.toDomain() = RepoContentItem(
    name = name ?: "unknown",
    type = type ?: "unknown",
    htmlUrl = htmlUrl ?: "https://http.cat/404"
)
