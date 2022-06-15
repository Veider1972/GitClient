package ru.veider.gitclient.data.entity

import com.google.gson.annotations.SerializedName

class GitUsersEntity(
    @SerializedName("login") val login: String,
    @SerializedName("id") val id: Long,
    @SerializedName("avatar_url") val avatarURL: String,
    @SerializedName("html_url") val htmlURL: String
)