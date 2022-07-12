package ru.veider.gitclient.data.retrofit

import io.reactivex.rxjava3.core.Single
import ru.veider.gitclient.di.inject
import ru.veider.gitclient.domain.entity.UserEntity
import ru.veider.gitclient.domain.repository.RemoteUsersRepository
import java.lang.Exception

class RemoteUsersRepositoryImpl : RemoteUsersRepository {

    private val gitHubApi: GitHubAPI by inject()
    private val path: String by inject("path")

    override fun getUsers(since: Long): Single<List<UserEntity>> {
        return gitHubApi.getUsers(since).map { usersList ->
            usersList.map { user ->
                user.toUserEntity(path)
            }
        }.doOnError { throw Exception() }
    }
}