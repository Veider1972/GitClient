package ru.veider.gitclient.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoomEntity(
    @PrimaryKey(autoGenerate = true)  val ndx: Long=0,
    val login: String = "",
    val id: Long = 0,
    val avatarURL: String = "",
    val htmlURL: String = ""
)