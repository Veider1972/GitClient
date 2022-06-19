package ru.veider.gitclient

import android.app.Application
import androidx.fragment.app.Fragment
import ru.veider.gitclient.data.retrofit.UsersRepositoryImpl
import ru.veider.gitclient.domain.repository.UsersRepository

class App : Application() {
    val usersRepository: UsersRepository by lazy { UsersRepositoryImpl() }
}

val Fragment.app: App get() = requireContext().applicationContext as App