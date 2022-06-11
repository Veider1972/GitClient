package ru.veider.gitclient.presenter.gitusers

import ru.veider.gitclient.domain.entity.GitUsersEntity
import ru.veider.gitclient.domain.repository.GitUsersRepository

class GitUsersPresenter(private var repository: GitUsersRepository):GitUsersContract.Presenter {

    private var view: GitUsersContract.View? = null
    private var usersList: List<GitUsersEntity>? = null
    private var showingProgress: Boolean = false

    override fun attach(view: GitUsersContract.View) {
        this.view = view
        usersList?.apply { view.showUsers(this) }
    }

    override fun detach() {
        view = null
    }

    override fun onRefresh() {
        usersList?.apply {view?.showProgress(false)} ?: loadData()
    }

    private fun loadData() {
        view?.apply {
            showProgress(true)
            showingProgress = true
            repository.getUsers(
                onSuccess = {
                    showProgress(false)
                    showingProgress = false
                    showUsers(it)
                    usersList = it
                },
                onError = {
                    showProgress(false)
                    showingProgress = false
                    showError(it)
                }
            )
        }
    }
}