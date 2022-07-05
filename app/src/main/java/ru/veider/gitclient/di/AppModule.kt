package ru.veider.gitclient.di

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.room.Room
import dagger.Module
import dagger.Provides
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
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule(private val app: App) {

    private val dbName: String = "RoomEntity.db"
    private val baseUrl: String = "https://api.github.com"

    @Provides
    @Singleton
    @Named("path")
    fun providePath(): String = app.applicationContext.dataDir.absolutePath

    @Singleton
    @Provides
    fun provideContext(): Context = app

    private fun usersDatabase() =
            Room.databaseBuilder(
                app.applicationContext,
                UsersDatabase::class.java,
                dbName
            )
                .allowMainThreadQueries()
                .build()

    private fun retrofit() =
            Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

    @Singleton
    @Provides
    fun app(): App = app

    @Singleton
    @Provides
    fun provideGitHubApi(): GitHubAPI {
        return retrofit().create(GitHubAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideUsersDao(): UsersDao {
        return usersDatabase().usersDao()
    }

    @Singleton
    @Provides
    fun provideUsersDatasource(): UsersDatasource {
        return UsersDatasource(provideUsersDao())
    }


    @Provides
    fun provideHandler(): Handler {
        return Handler(Looper.getMainLooper())
    }

    @Singleton
    @Provides
    fun provideLocalUsersRepository(): LocalUsersRepository {
        return LocalUsersRepositoryImpl(provideUsersDatasource())
    }

    @Singleton
    @Provides
    fun provideRemoteUsersRepository(): RemoteUsersRepository {
        return RemoteUsersRepositoryImpl(provideGitHubApi(), providePath())
    }

    @Singleton
    @Provides
    fun provideCachedUsersRepository(): CachedUsersRepository {
        return CachedUsersRepositoryImpl(provideRemoteUsersRepository(),
                                         provideLocalUsersRepository(),
                                         provideHandler(),
                                         provideContext()
        )
    }
}