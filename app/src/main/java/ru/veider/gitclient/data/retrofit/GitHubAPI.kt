package ru.veider.gitclient.data.retrofit

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubAPI {
    @GET("/users")
    fun getUsers(@Query("since")since:Long): Single<List<RemoteUserEntityDto>>
}