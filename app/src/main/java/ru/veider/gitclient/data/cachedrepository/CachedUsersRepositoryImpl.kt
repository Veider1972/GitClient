package ru.veider.gitclient.data.cachedrepository

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.veider.gitclient.App
import ru.veider.gitclient.R
import ru.veider.gitclient.data.retrofit.RemoteUsersRepositoryImpl
import ru.veider.gitclient.data.room.LocalUsersRepositoryImpl
import ru.veider.gitclient.domain.entity.UserEntity
import ru.veider.gitclient.domain.repository.CachedUsersRepository

class CachedUsersRepositoryImpl : CachedUsersRepository {

    private val TAG = "App ${this::class.java.simpleName} : ${this.hashCode()}"
    private val remoteRepository = RemoteUsersRepositoryImpl()
    private val localRepository = LocalUsersRepositoryImpl()

    override fun getUsers(): Single<List<UserEntity>> {
        return remoteRepository.getUsers()
            .doOnSuccess { userList ->
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(App.instance.applicationContext, App.instance.applicationContext.getString(R.string.web_data), Toast.LENGTH_SHORT).show()
                }

                localRepository.cleanUsers()
                for (user in userList) {
                    CoroutineScope(Dispatchers.Default).launch {
                        localRepository.insertUsers(user)
                    }
                }
            }
            .onErrorResumeWith {
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(App.instance.applicationContext, App.instance.applicationContext.getString(R.string.local_data), Toast.LENGTH_SHORT).show()
                }
                it.onSuccess(localRepository.getUsers())
            }
    }
}

