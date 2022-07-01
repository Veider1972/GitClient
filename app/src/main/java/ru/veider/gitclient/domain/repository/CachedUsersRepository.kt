package ru.veider.gitclient.domain.repository

import io.reactivex.rxjava3.core.Single
import ru.veider.gitclient.domain.entity.UserEntity

interface CachedUsersRepository{
    fun getUsers(since:Long) : Single<List<UserEntity>>
}