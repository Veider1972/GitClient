package ru.veider.gitclient.ui.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.veider.gitclient.R
import ru.veider.gitclient.databinding.ItemUsersBinding
import ru.veider.gitclient.domain.entity.UserEntity

class UsersViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_users, parent, false)) {

    private val binding = ItemUsersBinding.bind(itemView)

    fun onBind(gitUsersEntity: UserEntity) {
        binding.apply {
            userPhoto.load(gitUsersEntity.avatarURL)
            userLogin.text = gitUsersEntity.login
            userId.text = gitUsersEntity.id.toString()
            userUrl.text = gitUsersEntity.htmlURL
        }
    }
}