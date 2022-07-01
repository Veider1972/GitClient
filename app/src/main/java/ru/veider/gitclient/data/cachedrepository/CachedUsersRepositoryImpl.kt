package ru.veider.gitclient.data.cachedrepository

import android.app.Application
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
import ru.veider.gitclient.domain.repository.LocalUsersRepository
import ru.veider.gitclient.domain.repository.RemoteUsersRepository

class CachedUsersRepositoryImpl(private val remoteRepository: RemoteUsersRepository,
                                private val localRepository: LocalUsersRepository
) : CachedUsersRepository {

    private val TAG = "App ${this::class.java.simpleName} : ${this.hashCode()}"

    override fun getUsers(since:Long): Single<List<UserEntity>> {
        return remoteRepository.getUsers(since)
            .doOnSuccess { userList ->
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(App.instance.applicationContext, App.instance.applicationContext.getString(R.string.web_data), Toast.LENGTH_SHORT).show()
                }

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
                it.onSuccess(localRepository.getUsers(since))
            }
    }
}

