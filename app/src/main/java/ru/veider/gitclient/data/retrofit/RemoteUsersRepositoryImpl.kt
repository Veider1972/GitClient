package ru.veider.gitclient.data.retrofit

import io.reactivex.rxjava3.core.Single
import ru.veider.gitclient.App
import ru.veider.gitclient.domain.entity.UserEntity
import ru.veider.gitclient.domain.repository.RemoteUsersRepository
import java.lang.Exception
import javax.inject.Inject

class RemoteUsersRepositoryImpl @Inject constructor(private var gitHubApi: GitHubAPI,
                                                    private var path: String

) : RemoteUsersRepository {

    init {
        App.instance.appComponent.inject(this)
    }

    override fun getUsers(since: Long): Single<List<UserEntity>> {
        return gitHubApi.getUsers(since).map { usersList ->
            usersList.map { user ->
                user.toUserEntity(path)
            }
        }.doOnError { throw Exception() }
    }
}