package ru.veider.gitclient.ui.users

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import ru.veider.gitclient.domain.entity.UserEntity

interface UsersContract {

    interface ViewModel {
        val usersObserver: Observable<List<UserEntity>>
        val errorObserver: Observable<Throwable>
        val progressObserver: Observable<Boolean>
        val userPageObserver: Observable<UserEntity>
        val userNextObserver: Observable<Long>

        fun openUserPage(userEntity: UserEntity)
        fun nextUsers()
    }
}