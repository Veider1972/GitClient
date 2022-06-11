package ru.veider.gitclient.presenter.gitusers

import ru.veider.gitclient.domain.entity.GitUsersEntity

interface GitUsersContract {

    interface View {
        fun showUsers(users: List<GitUsersEntity>)
        fun showError(throwable: Throwable)
        fun showProgress(showingProgress: Boolean)
    }

    interface Presenter {
        fun attach(view: View)
        fun detach()

        fun onRefresh()
    }
}