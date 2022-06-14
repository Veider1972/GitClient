package ru.veider.gitclient.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.veider.gitclient.R
import ru.veider.gitclient.ui.gitusers.GitUsersFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.activity_main_container, GitUsersFragment.newInstance())
                .commit()
        }
    }
}