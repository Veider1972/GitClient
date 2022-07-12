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
import ru.veider.gitclient.domain.repository.LocalUsersRepository
import ru.veider.gitclient.domain.repository.RemoteUsersRepository
import kotlin.reflect.KClass

class DiModule(context: Context, di: Di) {
    private val baseUrl = "https://api.github.com"
    private val dbName = "RoomEntity.db"

    init {
        di.add(Context::class, Singleton{context})
        di.add(Retrofit::class, Singleton {
            Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        })
        di.add(GitHubAPI::class, Singleton { get<Retrofit>().create(GitHubAPI::class.java) })
        di.add(String::class, Singleton{get<Context>().cacheDir.absolutePath})
        di.add(RemoteUsersRepository::class, Singleton { RemoteUsersRepositoryImpl() })
        di.add(UsersDatabase::class, Singleton {
            Room.databaseBuilder(
                get(),
                UsersDatabase::class.java,
                dbName
            )
                .allowMainThreadQueries()
                .build()
        })
        di.add(UsersDao::class, Singleton{get<UsersDatabase>().usersDao()})
        di.add(UsersDatasource::class, Singleton{UsersDatasource()})
        di.add(LocalUsersRepository::class, Singleton{LocalUsersRepositoryImpl()})
        di.add(Handler::class, Fabric{Handler(Looper.getMainLooper())})
        di.add(CachedUsersRepository::class, Singleton { CachedUsersRepositoryImpl() })
    }
}