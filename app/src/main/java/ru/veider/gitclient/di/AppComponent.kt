package ru.veider.gitclient.di

import dagger.Component
import ru.veider.gitclient.data.retrofit.RemoteUsersRepositoryImpl
import ru.veider.gitclient.data.room.LocalUsersRepositoryImpl
import ru.veider.gitclient.data.room.UsersDatasource
import ru.veider.gitclient.ui.users.UsersFragment
import ru.veider.gitclient.ui.users.UsersViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(usersFragment:UsersFragment)
    fun inject(remoteUsersRepository: RemoteUsersRepositoryImpl)
    fun inject(localUsersRepository: LocalUsersRepositoryImpl)
    fun inject(usersDatasource: UsersDatasource)
    fun inject(usersViewModel: UsersViewModel)
}