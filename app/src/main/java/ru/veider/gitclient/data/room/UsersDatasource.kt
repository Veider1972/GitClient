package ru.veider.gitclient.data.room

import ru.veider.gitclient.App
import ru.veider.gitclient.domain.entity.UserEntity
import javax.inject.Inject

class UsersDatasource @Inject constructor(private var  usersDao: UsersDao) {

    init {
        App.instance.appComponent.inject(this)
    }

    fun getUsers(since:Long) = usersDao.getUsers(since).mapTo(ArrayList()){
        userEntityFromRoomEntity(it)
    }

    fun cleanUsers() {
        usersDao.cleanUsers()
    }

    fun insertUsers(user: UserEntity) {
        usersDao.insertUsers(roomEntityFromUserEntity(user))
    }

    private fun userEntityFromRoomEntity(entity: RoomEntity): UserEntity = UserEntity(
        entity.login,
        entity.id,
        entity.avatarURL,
        entity.htmlURL
    )

    private fun roomEntityFromUserEntity(entity: UserEntity): RoomEntity = RoomEntity(
        entity.id,
        entity.login,
        entity.avatarURL,
        entity.htmlURL
    )
}