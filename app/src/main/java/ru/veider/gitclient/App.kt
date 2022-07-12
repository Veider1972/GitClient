package ru.veider.gitclient

import android.app.Application
import androidx.fragment.app.Fragment
import ru.veider.gitclient.di.appModule

class App : Application() {

    companion object{
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        appModule.install()
    }
}

val Fragment.app: App get() = requireContext().applicationContext as App