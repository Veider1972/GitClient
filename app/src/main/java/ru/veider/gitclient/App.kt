package ru.veider.gitclient

import android.app.Application
import androidx.fragment.app.Fragment
import ru.veider.gitclient.di.Di
import ru.veider.gitclient.di.DiModule

class App : Application() {
    lateinit var di : Di

    override fun onCreate() {
        super.onCreate()
        di = Di().apply {
            DiModule(this@App,this)
        }
    }
}

val Fragment.app: App get() = requireContext().applicationContext as App