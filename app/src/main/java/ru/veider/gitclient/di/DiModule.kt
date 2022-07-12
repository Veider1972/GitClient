package ru.veider.gitclient.di

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.room.Room
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.veider.gitclient.App
import ru.veider.gitclient.data.cachedrepository.CachedUsersRepositoryImpl
import ru.veider.gitclient.data.retrofit.GitHubAPI
import ru.veider.gitclient.data.retrofit.RemoteUsersRepositoryImpl
import ru.veider.gitclient.data.room.LocalUsersRepositoryImpl
import ru.veider.gitclient.data.room.UsersDatabase
import ru.veider.gitclient.data.room.UsersDatasource
import ru.veider.gitclient.domain.repository.CachedUsersRepository
import ru.veider.gitclient.domain.repository.LocalUsersRepository
import ru.veider.gitclient.domain.repository.RemoteUsersRepository

val appModule = Module {
    singleton("baseUrl") { "https://api.github.com" }
    singleton("dbName") { "RoomEntity.db" }
    singleton<Context> { App.instance }
    singleton<Retrofit> {
        Retrofit.Builder()
            .baseUrl(get<String>("baseUrl"))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    singleton<GitHubAPI> { get<Retrofit>().create(GitHubAPI::class.java) }
    singleton("path") { get<Context>().cacheDir.absolutePath }
    singleton<RemoteUsersRepository> { RemoteUsersRepositoryImpl() }
    singleton {
        Room.databaseBuilder(
            get(),
            UsersDatabase::class.java,
            get("dbName")
        )
            .allowMainThreadQueries()
            .build()
    }
    singleton { get<UsersDatabase>().usersDao() }
    singleton { UsersDatasource() }
    singleton<LocalUsersRepository> { LocalUsersRepositoryImpl() }
    fabric { Handler(Looper.getMainLooper()) }
    singleton<CachedUsersRepository> { CachedUsersRepositoryImpl() }
}