package ru.veider.gitclient.data.retrofit

import androidx.core.graphics.drawable.toBitmap
import com.google.gson.annotations.SerializedName
import ru.veider.gitclient.domain.entity.UserEntity

class UserEntityDto(
    val login: String,
    val id: Long,
    @SerializedName("avatar_url") val avatarURL: String,
    @SerializedName("html_url") val htmlURL: String
) {
    fun toUserEntity() = UserEntity(login, id, avatarURL, htmlURL)

    companion object {
        fun fromUserEntity(userEntity: UserEntity) =
                UserEntityDto(userEntity.login,
                              userEntity.id,
                              userEntity.avatarURL,
                              userEntity.htmlURL
                )
    }
}