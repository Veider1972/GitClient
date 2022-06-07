package ru.veider.gitclient.data

import android.os.Handler
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.veider.gitclient.domain.entity.GitUsersEntity
import ru.veider.gitclient.domain.repository.GitUsersRepository
import java.lang.Error

class GitUsersRepositoryImpl : GitUsersRepository {

    private val gitHubApi = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://api.github.com")
        .build()
        .create(GitHubAPI::class.java)

    override fun getUsers(onSuccess: (List<GitUsersEntity>) -> Unit, onError: ((Throwable) -> Unit)?) {
        Handler().postDelayed({             // Имитация 3-х секундной задержки ответа...
            try {
                MainScope().launch {
                    val users: List<GitUsersEntity> = gitHubApi.getUsers()
                    if (users.isNotEmpty())
                        onSuccess(users)
                }
            } catch (e: Error) {
                onError?.invoke(Throwable(e.message))
            }

                              }, 3000L)
    }
}