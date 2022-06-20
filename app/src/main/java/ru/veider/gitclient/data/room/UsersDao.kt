package ru.veider.gitclient.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface UsersDao {
    @Query("SELECT * FROM RoomEntity")
    fun getUsers(): List<RoomEntity>

    @Query("DELETE FROM RoomEntity")
    fun cleanUsers()

    @Insert(onConflict = REPLACE)
    fun insertUsers(roomEntity: RoomEntity)


}