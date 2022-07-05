package ru.veider.gitclient.data.retrofit

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.google.gson.annotations.SerializedName
import ru.veider.gitclient.domain.entity.UserEntity
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class RemoteUserEntityDto(
    val login: String,
    val id: Long,
    @SerializedName("avatar_url") val avatarURL: String,
    @SerializedName("html_url") val htmlURL: String
) {
    fun toUserEntity(path: String): UserEntity {
        val url = URL(avatarURL)
        val bitmap = BitmapFactory.decodeStream(url.openStream())
        val fileName = path+"/$login.png"
        val file = File(fileName)
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
        } catch (e: Exception) {
            throw Exception(e.message.toString())
        } finally {
            fos?.close()
        }
        return UserEntity(login, id, fileName, htmlURL)
    }
}