package com.example.testtaskarkanit.data.network

import com.example.testtaskarkanit.data.network.model.toDomain
import com.example.testtaskarkanit.domain.model.repo.Repo
import com.example.testtaskarkanit.domain.model.repoContent.RepoContentItem
import com.example.testtaskarkanit.domain.model.user.User
import javax.inject.Inject

class RepositoryDataSource @Inject constructor(
    private val gitHubApi: GitHubApi
){
    suspend fun getUsers(query: String): User =
        gitHubApi.getUsers(query).toDomain()

    suspend fun getRepos(query: String): Repo =
        gitHubApi.getRepos(query).toDomain()

    suspend fun getRepositoryContent(
        owner: String,
        repo: String,
        path: String
    ): List<RepoContentItem> =
        gitHubApi.getRepositoryContent(owner, repo, path).map { it.toDomain() }
}