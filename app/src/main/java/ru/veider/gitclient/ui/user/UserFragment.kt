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

    lateinit var userEntity: UserEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.apply {
            val userEntity = this.getParcelable(UserEntity::class.java.toString()) as? UserEntity
            userEntity?.let {
                FragmentUserBinding.bind(view).apply {
                    avatar.load(userEntity.avatarURL)
                    login.text = userEntity.login
                }
            }
        }

    }
}