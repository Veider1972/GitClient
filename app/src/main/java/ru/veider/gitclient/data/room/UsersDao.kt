package ru.veider.gitclient.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface UsersDao {
    @Query("SELECT * FROM RoomEntity WHERE ID>:since AND ID<:since+46")
    fun getUsers(since:Long): List<RoomEntity>

    @Query("DELETE FROM RoomEntity")
    fun cleanUsers()

    @Insert(onConflict = REPLACE)
    fun insertUsers(roomEntity: RoomEntity)

}