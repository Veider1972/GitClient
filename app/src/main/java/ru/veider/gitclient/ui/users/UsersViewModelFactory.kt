package ru.veider.gitclient.ui.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.veider.gitclient.domain.repository.CachedUsersRepository

@Suppress("UNCHECKED_CAST")
class UsersViewModelFactory(private val cachedUsersRepository: CachedUsersRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return with(modelClass) {
            when {
                isAssignableFrom(UsersViewModel::class.java) -> UsersViewModel.getInstance(cachedUsersRepository)
                else                                            -> throw IllegalArgumentException("Unknown viewModel class $modelClass")
            }
        } as T
    }

    companion object {
        private var instance: UsersViewModelFactory? = null
        fun getInstance(cachedUsersRepository: CachedUsersRepository) = instance ?: synchronized(UsersViewModelFactory::class.java) {
            instance ?: UsersViewModelFactory(cachedUsersRepository).also { instance = it }
        }
    }
}