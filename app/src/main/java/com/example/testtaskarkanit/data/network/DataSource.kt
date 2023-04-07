package com.example.testtaskarkanit.data.network

import com.example.testtaskarkanit.data.network.model.toDomain
import com.example.testtaskarkanit.domain.model.repo.Repo
import com.example.testtaskarkanit.domain.model.user.User
import javax.inject.Inject

class DataSource @Inject constructor(
    private val gitHubApi: GitHubApi
){
    suspend fun getUsers(query: String): User =
        gitHubApi.getUsers(query).toDomain()

    suspend fun gerRepos(query: String): Repo =
        gitHubApi.getRepos(query).toDomain()
}