package ru.veider.gitclient.domain.repository

import ru.veider.gitclient.domain.entity.UserEntity

interface LocalUsersRepository {

    fun getUsers(): List<UserEntity>
    fun cleanUsers()
    fun insertUsers(user: UserEntity)
}