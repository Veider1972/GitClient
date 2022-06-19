package ru.veider.gitclient.data.retrofit

import android.graphics.BitmapFactory
import android.os.Handler
import coil.Coil
import coil.util.CoilUtils
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.subscribeBy
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.veider.gitclient.domain.entity.UserEntity
import ru.veider.gitclient.domain.repository.UsersRepository
import java.lang.Error

class UsersRepositoryImpl() : UsersRepository {

    private val gitHubApi = Retrofit.Builder()
        .baseUrl("https://api.github.com")
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GitHubAPI::class.java)

    override fun getUsers(onSuccess: (List<UserEntity>) -> Unit, onError: ((Throwable) -> Unit)?) {
        gitHubApi.getUsers().subscribeBy(
            onSuccess = { userList ->
                onSuccess.invoke(userList.map { it.toUserEntity()})
            }, onError = {
                onError?.invoke(it)
            }
        )
    }

    override fun getUsers(): Single<List<UserEntity>> = gitHubApi.getUsers().map { usersList ->
        usersList.map { user ->
            user.toUserEntity()
        }
    }
}