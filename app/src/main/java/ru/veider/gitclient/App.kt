package ru.veider.gitclient

import android.app.Application
import androidx.fragment.app.Fragment
import ru.veider.gitclient.data.cachedrepository.CachedUsersRepositoryImpl
import ru.veider.gitclient.domain.repository.CachedUsersRepository

class App : Application() {
    companion object{
        lateinit var instance: App
        val cachedUsersRepository: CachedUsersRepository by lazy { CachedUsersRepositoryImpl() }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}

val Fragment.app: App get() = requireContext().applicationContext as App