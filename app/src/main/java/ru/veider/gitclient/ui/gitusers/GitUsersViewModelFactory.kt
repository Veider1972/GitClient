package ru.veider.gitclient.ui.gitusers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.veider.gitclient.domain.repository.GitUsersRepository

@Suppress("UNCHECKED_CAST")
class GitUsersViewModelFactory(private val gitUsersRepository: GitUsersRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return with(modelClass) {
            when {
                isAssignableFrom(GitUsersViewModel::class.java) -> GitUsersViewModel.getInstance(gitUsersRepository)
                else                                            -> throw IllegalArgumentException("Unknown viewModel class $modelClass")
            }
        } as T
    }

    companion object {
        private var instance: GitUsersViewModelFactory? = null
        fun getInstance(gitUsersRepository: GitUsersRepository) = instance ?: synchronized(GitUsersViewModelFactory::class.java) {
            instance ?: GitUsersViewModelFactory(gitUsersRepository).also { instance = it }
        }
    }
}