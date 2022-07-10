package ru.veider.gitclient

import android.app.Application
import androidx.fragment.app.Fragment
import ru.veider.gitclient.di.Injection
import ru.veider.gitclient.di.InjectionImpl

class App : Application() {
    lateinit var injection : Injection

    override fun onCreate() {
        super.onCreate()
        injection = InjectionImpl(this)
    }
}

val Fragment.app: App get() = requireContext().applicationContext as App