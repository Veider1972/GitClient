package ru.veider.gitclient.ui.gitusers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.veider.gitclient.domain.entity.GitUsersEntity
import ru.veider.gitclient.domain.repository.GitUsersRepository
import ru.veider.gitclient.ui.utils.SingleEventLiveData

class GitUsersViewModel(
    private val gitUsersRepository: GitUsersRepository
) : ViewModel(), GitUsersContract.ViewModel {

    override val usersLiveData: LiveData<List<GitUsersEntity>> = MutableLiveData()
    override val errorLiveData: LiveData<Throwable> = SingleEventLiveData()
    override val progressLiveData: LiveData<Boolean> = MutableLiveData()
    override val userPageLiveData: LiveData<String> = SingleEventLiveData()

    companion object {
        private var instance: GitUsersViewModel? = null
        fun getInstance(gitUsersRepository: GitUsersRepository) =
                instance?.apply {} ?: GitUsersViewModel(gitUsersRepository).also { instance = it }
    }

    init {
        loadData()
    }

    override fun openUserPage(url: String) {
        if (url.isNotEmpty())
            userPageLiveData.mutable().postValue(url)
    }

    private fun loadData() {
        progressLiveData.mutable().postValue(true)
        gitUsersRepository.getUsers(
            onSuccess = {
                progressLiveData.mutable().postValue(false)
                usersLiveData.mutable().postValue(it)
            },
            onError = {
                progressLiveData.mutable().postValue(false)
                errorLiveData.mutable().postValue(it)
            }
        )
    }

    private fun <T> LiveData<T>.mutable(): MutableLiveData<T> {
        return this as? MutableLiveData<T>
                ?: throw IllegalStateException("It's not a MutableLiveData")
    }
}