package ru.veider.gitclient.ui.gitusers

import androidx.lifecycle.LiveData
import ru.veider.gitclient.domain.entity.GitUsersData

interface GitUsersContract {

    interface ViewModel {
        val usersLiveData: LiveData<List<GitUsersData>>
        val errorLiveData: LiveData<Throwable>
        val progressLiveData: LiveData<Boolean>
        val userPageLiveData: LiveData<String>

        fun openUserPage(url: String)
    }
}