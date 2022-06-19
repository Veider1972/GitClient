package ru.veider.gitclient.ui.users

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.SingleSubject
import ru.veider.gitclient.domain.entity.UserEntity
import ru.veider.gitclient.domain.repository.UsersRepository

class UsersViewModel(
    private val usersRepository: UsersRepository
) : ViewModel(), UsersContract.ViewModel {

    override val usersLiveData: Observable<List<UserEntity>> = BehaviorSubject.create()
    override val errorLiveData: Observable<Throwable> = BehaviorSubject.create()
    override val progressLiveData: Observable<Boolean> = BehaviorSubject.create()
    override var userPageLiveData: Observable<String> = BehaviorSubject.create()

    companion object {
        private var instance: UsersViewModel? = null
        fun getInstance(usersRepository: UsersRepository) =
                instance?.apply {} ?: UsersViewModel(usersRepository).also { instance = it }
    }

    init {
        loadData()
    }

    override fun openUserPage(url: String) {
        if (url.isNotEmpty()) {
            userPageLiveData.mutable().onNext(url)
        }
    }

    private fun loadData() {
        progressLiveData.mutable().onNext(true)
        usersRepository.getUsers()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    progressLiveData.mutable().onNext(false)
                    usersLiveData.mutable().onNext(it)
                },
                onError = {
                    progressLiveData.mutable().onNext(false)
                    errorLiveData.mutable().onNext(it)
                }
            )
    }

    private fun <T> Observable<T>.mutable(): BehaviorSubject<T> {
        return this as? BehaviorSubject<T>
                ?: throw IllegalStateException("It's not a BehaviorSubject")
    }

    private fun <T> Single<T>.single(): SingleSubject<T> {
        return this as? SingleSubject<T>
                ?: throw IllegalStateException("It's not a SingleSubject")
    }
}