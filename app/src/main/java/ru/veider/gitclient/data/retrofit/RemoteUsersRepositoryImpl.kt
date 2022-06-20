package ru.veider.gitclient.data.retrofit

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.subscribeBy
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.veider.gitclient.domain.entity.UserEntity
import ru.veider.gitclient.domain.repository.RemoteUsersRepository
import java.lang.Exception

class RemoteUsersRepositoryImpl : RemoteUsersRepository {

    private val gitHubApi = Retrofit.Builder()
        .baseUrl("https://api.github.com")
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GitHubAPI::class.java)

    override fun getUsers(): Single<List<UserEntity>> {
        return gitHubApi.getUsers().map { usersList ->
            usersList.map { user ->
                user.toUserEntity()
            }
        }.doOnError { throw Exception() }
    }
}