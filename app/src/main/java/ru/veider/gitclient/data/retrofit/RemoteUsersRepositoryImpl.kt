package ru.veider.gitclient.data.retrofit

import io.reactivex.rxjava3.core.Single
import ru.veider.gitclient.domain.entity.UserEntity
import ru.veider.gitclient.domain.repository.RemoteUsersRepository
import java.lang.Exception

class RemoteUsersRepositoryImpl(
    private val gitHubApi: GitHubAPI,
    private val pathForTempImages: String
) : RemoteUsersRepository {

    override fun getUsers(since:Long): Single<List<UserEntity>> {
        return gitHubApi.getUsers(since).map { usersList ->
            usersList.map { user ->
                user.toUserEntity(pathForTempImages)
            }
        }.doOnError { throw Exception() }
    }
}