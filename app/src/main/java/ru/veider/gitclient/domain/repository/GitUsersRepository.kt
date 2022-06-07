package ru.veider.gitclient.domain.repository

import ru.veider.gitclient.domain.entity.GitUsersEntity

interface GitUsersRepository {
    fun getUsers(
        onSuccess: (List<GitUsersEntity>) -> Unit,
        onError: ((Throwable) -> Unit)? = null
    )
}