package ru.veider.gitclient.ui.users

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleSource
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.SingleSubject
import ru.veider.gitclient.domain.entity.UserEntity
import ru.veider.gitclient.domain.repository.CachedUsersRepository

class UsersViewModel(
    private val cachedUsersRepository: CachedUsersRepository
) : ViewModel(), UsersContract.ViewModel {

    override val usersObserver: Observable<List<UserEntity>> = BehaviorSubject.create()
    override val errorObserver: Observable<Throwable> = BehaviorSubject.create()
    override val progressObserver: Observable<Boolean> = BehaviorSubject.create()
    override var userPageObserver: Observable<UserEntity> = BehaviorSubject.create()
    override val userNextObserver: Observable<Long> = BehaviorSubject.create()
    private lateinit var usersList: List<UserEntity>
    private val userNext = userNextObserver.subscribe{
        loadData(it)
    }

    companion object {
        private var instance: UsersViewModel? = null
        fun getInstance(cachedRemoteUsersRepository: CachedUsersRepository) =
                instance?.apply {} ?: UsersViewModel(cachedRemoteUsersRepository).also { instance = it }
    }

    init {
        loadData(0)
    }

    override fun openUserPage(userEntity: UserEntity) {
        userPageObserver.mutable().onNext(userEntity)
    }

    override fun nextUsers(){
        userNextObserver.mutable().onNext(usersList.last().id)
    }

    private fun loadData(since: Long) {
        progressObserver.mutable().onNext(true)
        cachedUsersRepository.getUsers(since)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    usersList = it
                    progressObserver.mutable().onNext(false)
                    usersObserver.mutable().onNext(it)
                },
                onError = {
                    progressObserver.mutable().onNext(false)
                    errorObserver.mutable().onNext(it)
                }
            )
    }

    private fun <T : Any> Observable<T>.mutable(): BehaviorSubject<T> {
        return this as? BehaviorSubject<T>
                ?: throw IllegalStateException("It's not a BehaviorSubject")
    }
}