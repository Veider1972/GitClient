package ru.veider.gitclient.ui.gitusers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.veider.gitclient.R
import ru.veider.gitclient.databinding.GitUsersItemBinding
import ru.veider.gitclient.domain.entity.GitUsersEntity

class GitUsersViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.git_users_item, parent, false)) {

    private val binding = GitUsersItemBinding.bind(itemView)

    fun onBind(gitUsersEntity: GitUsersEntity) {
        binding.apply {
            userPhoto.load(gitUsersEntity.avatar_url)
            userLogin.text = gitUsersEntity.login
            userId.text = gitUsersEntity.id.toString()
            userUrl.text = gitUsersEntity.html_url
        }
    }
}