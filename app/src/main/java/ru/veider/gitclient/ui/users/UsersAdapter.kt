package ru.veider.gitclient.ui.users

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.veider.gitclient.domain.entity.UserEntity

class UsersAdapter(
    private val onItemClick: UsersViewHolder.OnItemClick
) : RecyclerView.Adapter<UsersViewHolder>() {

    init {
        setHasStableIds(true)
    }

    private var users = mutableListOf<UserEntity>()

    override fun getItemId(position: Int) = users[position].id

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UsersViewHolder(parent, onItemClick)

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.onBind(users[position])
    }

    override fun getItemCount() = users.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(users: List<UserEntity>) {
        this.users.clear()
        this.users.addAll(users)
        notifyDataSetChanged()
    }
}