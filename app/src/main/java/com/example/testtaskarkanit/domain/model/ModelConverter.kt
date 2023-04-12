package com.example.testtaskarkanit.domain.model

import com.example.testtaskarkanit.domain.model.repo.ItemRepo
import com.example.testtaskarkanit.domain.model.repo.Owner
import com.example.testtaskarkanit.domain.model.repo.Repo
import com.example.testtaskarkanit.domain.model.repoContent.RepoContentItem
import com.example.testtaskarkanit.domain.model.user.ItemUser
import com.example.testtaskarkanit.domain.model.user.User
import com.example.testtaskarkanit.presentation.model.*
import com.example.testtaskarkanit.presentation.model.repoContent.RepoContentItemType
import com.example.testtaskarkanit.presentation.model.repoContent.RepoContentItemUI

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
    htmlUrl = htmlUrl,
    ownerUI = owner?.toUI()
)

fun Owner.toUI() = OwnerUI(
    login = login
)

fun RepoContentItem.toUI() = RepoContentItemUI(
    name = name,
    type = RepoContentItemType.parseRepoContentType(type),
    htmlUrl = htmlUrl
)