package ru.veider.gitclient.domain.repository

import ru.veider.gitclient.domain.entity.UserEntity

interface LocalUsersRepository {
    fun getUsers(since:Long): List<UserEntity>
    fun cleanUsers()
    fun insertUsers(user: UserEntity)
}