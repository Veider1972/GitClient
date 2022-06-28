package ru.veider.gitclient.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoomEntity(
    @PrimaryKey(autoGenerate = false) val id: Long = 0,
    val login: String = "",
    val avatarURL: String = "",
    val htmlURL: String = ""
)