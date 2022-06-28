package ru.veider.gitclient.data.room

import ru.veider.gitclient.domain.entity.UserEntity
import ru.veider.gitclient.domain.repository.LocalUsersRepository

class LocalUsersRepositoryImpl:LocalUsersRepository {

    private var db: UsersDatasource = UsersDatasource()

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