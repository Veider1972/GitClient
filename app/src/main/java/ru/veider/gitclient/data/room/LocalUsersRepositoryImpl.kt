package ru.veider.gitclient.data.room

import ru.veider.gitclient.di.inject
import ru.veider.gitclient.domain.entity.UserEntity
import ru.veider.gitclient.domain.repository.LocalUsersRepository

class LocalUsersRepositoryImpl():LocalUsersRepository {

    private val db: UsersDatasource by inject()

    override fun getUsers(since:Long): List<UserEntity> {
        return db.getUsers(since)
    }

    override fun cleanUsers() {
        db.cleanUsers()
    }

    override fun insertUsers(user: UserEntity) {
        db.insertUsers(user)
    }
}