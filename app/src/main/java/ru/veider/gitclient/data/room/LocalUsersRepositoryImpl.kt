package ru.veider.gitclient.data.room

import ru.veider.gitclient.App
import ru.veider.gitclient.domain.entity.UserEntity
import ru.veider.gitclient.domain.repository.LocalUsersRepository
import javax.inject.Inject

class LocalUsersRepositoryImpl @Inject constructor(private var  db: UsersDatasource):LocalUsersRepository {

    init {
        App.instance.appComponent.inject(this)
    }

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