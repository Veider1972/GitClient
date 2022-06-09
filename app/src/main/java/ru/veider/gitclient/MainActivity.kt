package ru.veider.gitclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.veider.gitclient.ui.gitusers.GitUsersFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, GitUsersFragment.newInstance())
                .commit()
        }
    }
}