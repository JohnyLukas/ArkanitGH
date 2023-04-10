package com.example.testtaskarkanit.domain.model

import com.example.testtaskarkanit.presentation.model.*

fun User.toUI() = UserUI(
    items = items.map { it.toUI() }
)

fun ItemUser.toUI() = ItemUserUI(
    login = login,
    avatarUrl = avatarUrl,
    htmlUrl = htmlUrl,
    score = score
)

fun Repo.toUI() = RepoUI(
    items = items.map { it.toUI() }
)

fun ItemRepo.toUI() = ItemRepoUI(
    name = name,
    description = description,
    forksCount = forksCount,
    htmlUrl = htmlUrl
)