package ru.veider.gitclient.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import ru.veider.gitclient.R
import ru.veider.gitclient.ui.users.UsersFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.activity_main_container, UsersFragment())
                .commit()
        }
    }

    override fun onBackPressed() {
//        val backStackCount = supportFragmentManager.backStackEntryCount
//        if (backStackCount==0)
            super.onBackPressed()
//        else
//            supportFragmentManager.popBackStack(1,0)
    }
}