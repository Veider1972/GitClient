package ru.veider.gitclient.data

import retrofit2.http.GET
import ru.veider.gitclient.data.entity.GitUsersEntity

interface GitHubAPI {
    @GET("/users")
    suspend fun getUsers(): List<GitUsersEntity>
}