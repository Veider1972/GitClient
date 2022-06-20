package ru.veider.gitclient.data.room

import androidx.room.Room
import ru.veider.gitclient.App
import ru.veider.gitclient.domain.entity.UserEntity

class UsersDatasource {
    private val DB_NAME = "RoomEntity.db"
    private var db: UsersDatabase = Room.databaseBuilder(
        App.instance.baseContext,
        UsersDatabase::class.java,
        DB_NAME
    )
        .allowMainThreadQueries()
        .build()

    fun getUsers() = db.usersDao().getUsers().mapTo(ArrayList()){
        userEntityFromRoomEntity(it)
    }

    fun cleanUsers() {
        db.usersDao().cleanUsers()
    }

    fun insertUsers(user: UserEntity) {
        db.usersDao().insertUsers(roomEntityFromUserEntity(user))
    }

    private fun userEntityFromRoomEntity(entity: RoomEntity): UserEntity = UserEntity(
        entity.login,
        entity.id,
        entity.avatarURL,
        entity.htmlURL
    )

    private fun roomEntityFromUserEntity(entity: UserEntity): RoomEntity = RoomEntity(
        0,
        entity.login,
        entity.id,
        entity.avatarURL,
        entity.htmlURL
    )
}