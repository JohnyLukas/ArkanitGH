package com.example.testtaskarkanit.data.network

import com.example.testtaskarkanit.data.network.model.repo.RepoResponse
import com.example.testtaskarkanit.data.network.model.user.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApi {
    @GET("search/users")
    suspend fun getUsers(@Query("q") userName: String): UserResponse

    @GET("search/repositories")
    suspend fun getRepos(@Query("q") repoName: String): RepoResponse
}