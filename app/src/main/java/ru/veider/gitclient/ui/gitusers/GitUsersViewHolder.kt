package ru.veider.gitclient.ui.gitusers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.veider.gitclient.R
import ru.veider.gitclient.databinding.ItemUsersBinding
import ru.veider.gitclient.domain.entity.GitUsersEntity

class GitUsersViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_users, parent, false)) {

    private val binding = ItemUsersBinding.bind(itemView)

    fun onBind(gitUsersEntity: GitUsersEntity) {
        binding.apply {
            userPhoto.load(gitUsersEntity.avatarURL)
            userLogin.text = gitUsersEntity.login
            userId.text = gitUsersEntity.id.toString()
            userUrl.text = gitUsersEntity.htmlURL
        }
    }
}