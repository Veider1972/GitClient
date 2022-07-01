package ru.veider.gitclient

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import ru.veider.gitclient.data.cachedrepository.CachedUsersRepositoryImpl
import ru.veider.gitclient.data.retrofit.RemoteUsersRepositoryImpl
import ru.veider.gitclient.data.room.LocalUsersRepositoryImpl
import ru.veider.gitclient.domain.repository.CachedUsersRepository

class App : Application() {
    companion object{
        lateinit var instance: App
    }

    val cachedUsersRepository: CachedUsersRepository by lazy { CachedUsersRepositoryImpl(RemoteUsersRepositoryImpl(), LocalUsersRepositoryImpl()) }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}

val Fragment.app: App get() = requireContext().applicationContext as App
val Context.app: App get() = applicationContext as App