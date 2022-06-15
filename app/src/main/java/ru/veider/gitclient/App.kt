package ru.veider.gitclient

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import ru.veider.gitclient.data.GitUsersRepositoryImpl
import ru.veider.gitclient.domain.repository.GitUsersRepository
import ru.veider.gitclient.ui.gitusers.GitUsersContract

class App : Application() {
    val gitUsersRepository: GitUsersRepository by lazy { GitUsersRepositoryImpl() }
}

val Fragment.app: App get() = requireContext().applicationContext as App