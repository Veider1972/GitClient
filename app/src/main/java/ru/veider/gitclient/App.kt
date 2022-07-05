package ru.veider.gitclient

import android.app.Application
import ru.veider.gitclient.di.AppComponent
import ru.veider.gitclient.di.AppModule
import ru.veider.gitclient.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}
