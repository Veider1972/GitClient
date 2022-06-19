package ru.veider.gitclient.ui.users

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.subjects.BehaviorSubject
import ru.veider.gitclient.domain.entity.UserEntity

interface UsersContract {

    interface ViewModel {
        val usersLiveData: Observable<List<UserEntity>>
        val errorLiveData: Observable<Throwable>
        val progressLiveData: Observable<Boolean>
        val userPageLiveData: Observable<String>

        fun openUserPage(url: String)
    }
}