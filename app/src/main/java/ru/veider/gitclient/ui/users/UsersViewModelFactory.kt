package ru.veider.gitclient.ui.gitusers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.veider.gitclient.domain.repository.UsersRepository
import ru.veider.gitclient.ui.users.UsersViewModel

@Suppress("UNCHECKED_CAST")
class UsersViewModelFactory(private val gitUsersRepository: UsersRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return with(modelClass) {
            when {
                isAssignableFrom(UsersViewModel::class.java) -> UsersViewModel.getInstance(gitUsersRepository)
                else                                            -> throw IllegalArgumentException("Unknown viewModel class $modelClass")
            }
        } as T
    }

    companion object {
        private var instance: UsersViewModelFactory? = null
        fun getInstance(gitUsersRepository: UsersRepository) = instance ?: synchronized(UsersViewModelFactory::class.java) {
            instance ?: UsersViewModelFactory(gitUsersRepository).also { instance = it }
        }
    }
}