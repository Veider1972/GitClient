package ru.veider.gitclient.ui.gitusers

import androidx.lifecycle.LiveData
import ru.veider.gitclient.domain.entity.GitUsersEntity

interface GitUsersContract {

    interface ViewModel {
        val usersLiveData: LiveData<List<GitUsersEntity>>
        val errorLiveData: LiveData<Throwable>
        val progressLiveData: LiveData<Boolean>
        val userPageLiveData: LiveData<String>

        fun openUserPage(url: String)
    }
}