package ru.veider.gitclient.ui.user

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.veider.gitclient.R
import ru.veider.gitclient.databinding.FragmentUserBinding
import ru.veider.gitclient.domain.entity.UserEntity

class UserFragment : Fragment(R.layout.fragment_user) {

    private val TAG = "App ${this::class.java.simpleName} : ${this.hashCode()}"

    private var isPageLoaded = false
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
            avatar.setImageDrawable(userEntity.avatar)
            login.text = userEntity.login
        }
    }

}