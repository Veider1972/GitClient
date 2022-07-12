package ru.veider.gitclient.data.cachedrepository

import android.content.Context
import android.os.Handler
import android.widget.Toast
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.veider.gitclient.R
import ru.veider.gitclient.di.inject
import ru.veider.gitclient.domain.entity.UserEntity
import ru.veider.gitclient.domain.repository.CachedUsersRepository
import ru.veider.gitclient.domain.repository.LocalUsersRepository
import ru.veider.gitclient.domain.repository.RemoteUsersRepository

class CachedUsersRepositoryImpl : CachedUsersRepository {

    private val TAG = "App ${this::class.java.simpleName} : ${this.hashCode()}"

    private val remoteRepository: RemoteUsersRepository by inject()
    private val localRepository: LocalUsersRepository by inject()
    private val handler: Handler by inject()
    private val context : Context by inject()

    override fun getUsers(since:Long): Single<List<UserEntity>> {
        return remoteRepository.getUsers(since)
            .doOnSuccess { userList ->
                handler.post {
                    Toast.makeText(context, context.getString(R.string.web_data), Toast.LENGTH_SHORT).show()
                }

                for (user in userList) {
                    CoroutineScope(Dispatchers.Default).launch {
                        localRepository.insertUsers(user)
                    }
                }
            }
            .onErrorResumeWith {
                handler.post {
                    Toast.makeText(context, context.getString(R.string.local_data), Toast.LENGTH_SHORT).show()
                }
                it.onSuccess(localRepository.getUsers(since))
            }
    }
}

