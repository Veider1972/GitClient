package ru.veider.gitclient.data.retrofit

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import com.google.gson.annotations.SerializedName
import ru.veider.gitclient.domain.entity.UserEntity
import java.net.URL

class UserEntityDto(
    val login: String,
    val id: Long,
    @SerializedName("avatar_url") val avatarURL: String,
    @SerializedName("html_url") val htmlURL: String
) {
    fun toUserEntity() : UserEntity{
        val url = URL(avatarURL)
        val drawable = BitmapDrawable.createFromStream(url.openStream(),login) as BitmapDrawable
        return UserEntity(login, id, drawable, htmlURL)
    }

}