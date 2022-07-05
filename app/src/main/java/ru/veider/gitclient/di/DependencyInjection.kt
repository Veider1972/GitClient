package ru.veider.gitclient.di

import android.os.Handler
import android.os.Looper
import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.veider.gitclient.appContext
import ru.veider.gitclient.data.cachedrepository.CachedUsersRepositoryImpl
import ru.veider.gitclient.data.retrofit.GitHubAPI
import ru.veider.gitclient.data.retrofit.RemoteUsersRepositoryImpl
import ru.veider.gitclient.data.room.LocalUsersRepositoryImpl
import ru.veider.gitclient.data.room.UsersDatabase
import ru.veider.gitclient.data.room.UsersDatasource
import ru.veider.gitclient.domain.repository.CachedUsersRepository
import ru.veider.gitclient.domain.repository.LocalUsersRepository
import ru.veider.gitclient.domain.repository.RemoteUsersRepository
import ru.veider.gitclient.ui.users.UsersViewModel

val appModule = module {
    single(named("context")) { androidContext() }
    single(named("dbName")) { "RoomEntity.db" }
    single(named("baseUrl")) { "https://api.github.com" }
    single(named("usersDatabase")) {
        Room.databaseBuilder(
            androidContext(),
            UsersDatabase::class.java,
            get(named("dbName"))
        )
            .allowMainThreadQueries()
            .build()
    }
    single<Retrofit>(named("retrofit")) {
        Retrofit.Builder()
            .baseUrl(get<String>(named("baseUrl")))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single<GitHubAPI>(named("gitHubAPI")) { get<Retrofit>(named("retrofit")).create(GitHubAPI::class.java) }
    single(named("usersDao")) { get<UsersDatabase>(named("usersDatabase")).usersDao() }
    single(named("usersDataSource")) { UsersDatasource(get(named("usersDao"))) }
    factory(named("handler")) { Handler(Looper.getMainLooper()) }
    single<LocalUsersRepository>(named("localRepository")) { LocalUsersRepositoryImpl(get(named("usersDataSource"))) }
    single<RemoteUsersRepository>(named("remoteRepository")) { RemoteUsersRepositoryImpl(get(named("gitHubAPI")), androidContext().cacheDir.absolutePath) }
    single<CachedUsersRepository> {
        CachedUsersRepositoryImpl(get(named("remoteRepository")),
                                  get(named("localRepository")),
                                  get(named("handler")),
                                  androidContext()
        )
    }
    viewModel {
        UsersViewModel(get())
    }
}