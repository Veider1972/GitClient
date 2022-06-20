package ru.veider.gitclient.ui.users

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.BehaviorSubject
import ru.veider.gitclient.domain.entity.UserEntity
import ru.veider.gitclient.domain.repository.CachedUsersRepository

class UsersViewModel(
    private val cachedUsersRepository: CachedUsersRepository
) : ViewModel(), UsersContract.ViewModel {

    override val usersObserver: Observable<List<UserEntity>> = BehaviorSubject.create()
    override val errorObserver: Observable<Throwable> = BehaviorSubject.create()
    override val progressObserver: Observable<Boolean> = BehaviorSubject.create()
    override var userPageObserver: Observable<UserEntity> = BehaviorSubject.create()

    companion object {
        private var instance: UsersViewModel? = null
        fun getInstance(cachedRemoteUsersRepository: CachedUsersRepository) =
                instance?.apply {} ?: UsersViewModel(cachedRemoteUsersRepository).also { instance = it }
    }

    init {
        loadData()
    }

    override fun openUserPage(userEntity: UserEntity) {
        userPageObserver.mutable().onNext(userEntity)
    }

    private fun loadData() {
        progressObserver.mutable().onNext(true)
        cachedUsersRepository.getUsers()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
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