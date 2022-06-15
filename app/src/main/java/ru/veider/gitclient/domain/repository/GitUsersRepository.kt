package ru.veider.gitclient.domain.repository

import ru.veider.gitclient.domain.entity.GitUsersData

interface GitUsersRepository {
    fun getUsers(
        onSuccess: (List<GitUsersData>) -> Unit,
        onError: ((Throwable) -> Unit)? = null
    )
}