package ru.veider.gitclient

import android.app.Application
import androidx.fragment.app.Fragment
import ru.veider.gitclient.di.Injection

class App : Application() {
    lateinit var injection : Injection

    override fun onCreate() {
        super.onCreate()
        injection = Injection(this)
    }
}

val Fragment.app: App get() = requireContext().applicationContext as App