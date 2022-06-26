package ru.veider.gitclient.ui.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.veider.gitclient.R
import ru.veider.gitclient.databinding.ItemUsersBinding
import ru.veider.gitclient.domain.entity.UserEntity

class UsersViewHolder(parent: ViewGroup, private val onItemClick: (UserEntity)->Unit) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_users, parent, false)) {

    private val binding = ItemUsersBinding.bind(itemView)

    fun onBind(usersEntity: UserEntity) {
        binding.apply {
            userPhoto.load(usersEntity.avatarURL)
            userLogin.text = usersEntity.login
            userId.text = usersEntity.id.toString()
            userUrl.text = usersEntity.htmlURL
            itemView.setOnClickListener {
                onItemClick(usersEntity)
            }
        }
    }
}