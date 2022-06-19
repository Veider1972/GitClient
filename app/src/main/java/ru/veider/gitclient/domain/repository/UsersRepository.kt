package ru.veider.gitclient.domain.repository

import io.reactivex.rxjava3.core.Single
import ru.veider.gitclient.domain.entity.UserEntity

interface UsersRepository {
    fun getUsers(
        onSuccess: (List<UserEntity>) -> Unit,
        onError: ((Throwable) -> Unit)? = null
    )
    fun getUsers() : Single<List<UserEntity>>
}