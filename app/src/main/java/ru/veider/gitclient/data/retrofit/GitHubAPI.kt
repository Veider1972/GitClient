package ru.veider.gitclient.data.retrofit

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface GitHubAPI {
    @GET("/users")
    fun getUsers(): Single<List<UserEntityDto>>
}