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
import ru.veider.gitclient.data.room.UsersDao
import ru.veider.gitclient.data.room.UsersDatabase
import ru.veider.gitclient.data.room.UsersDatasource
import ru.veider.gitclient.domain.repository.CachedUsersRepository
import kotlin.reflect.KClass

class DiModule(context: Context, di:Di) {
    private val baseUrl = "https://api.github.com"
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private val gitHubAPI: GitHubAPI by lazy { retrofit.create(GitHubAPI::class.java) }

    private val remoteRepository by lazy { RemoteUsersRepositoryImpl(gitHubAPI, context.cacheDir.absolutePath) }
    private val dbName = "RoomEntity.db"
    private val usersDatabase: UsersDatabase = Room.databaseBuilder(
        context,
        UsersDatabase::class.java,
        dbName
    )
        .allowMainThreadQueries()
        .build()

    private var usersDao: UsersDao = usersDatabase.usersDao()
    private val usersDataSource by lazy { UsersDatasource(usersDao) }
    private val localRepository by lazy { LocalUsersRepositoryImpl(usersDataSource) }
    private val handler by lazy { Handler(Looper.getMainLooper()) }
    val cachedUsersRepository: CachedUsersRepository by lazy { CachedUsersRepositoryImpl(remoteRepository, localRepository, handler, context.applicationContext) }
    init{
        di.add(CachedUsersRepository::class, cachedUsersRepository)
    }
}