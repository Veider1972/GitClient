package ru.veider.gitclient.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import coil.load
import ru.veider.gitclient.R
import ru.veider.gitclient.databinding.FragmentUserBinding
import ru.veider.gitclient.domain.entity.UserEntity

class UserFragment : Fragment(R.layout.fragment_user) {

    private val TAG = "App ${this::class.java.simpleName} : ${this.hashCode()}"

    companion object {
        private lateinit var userEntity: UserEntity

        fun newInstance(userEntity: UserEntity): UserFragment {
            this.userEntity =  userEntity
            return UserFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentUserBinding.bind(view).apply {
            avatar.load(userEntity.avatarURL)
            login.text = userEntity.login
        }
    }
}