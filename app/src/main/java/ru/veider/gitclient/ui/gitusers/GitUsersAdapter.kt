package ru.veider.gitclient.ui.gitusers

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.veider.gitclient.domain.entity.GitUsersEntity

class GitUsersAdapter : RecyclerView.Adapter<GitUsersViewHolder>() {

    init {
        setHasStableIds(true)
    }

    private var users = mutableListOf<GitUsersEntity>()

    override fun getItemId(position: Int) = users[position].id

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GitUsersViewHolder(parent)

    override fun onBindViewHolder(holder: GitUsersViewHolder, position: Int) {
        holder.onBind(users[position])
    }

    override fun getItemCount() = users.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(users: List<GitUsersEntity>) {
        this.users.clear()
        this.users.addAll(users)
        notifyDataSetChanged()
    }
}