package ru.veider.gitclient.ui.users

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.veider.gitclient.domain.entity.UserEntity

class UsersAdapter(
    val usersFragment: UsersFragment
) : RecyclerView.Adapter<UsersViewHolder>() {

    init {
        setHasStableIds(true)
    }

    interface OnItemClick{
        fun onUserSelect(url:String)
    }

    private var users = mutableListOf<UserEntity>()

    override fun getItemId(position: Int) = users[position].id

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UsersViewHolder(parent).apply {
        itemView.setOnClickListener {
            usersFragment.onUserSelect(users[adapterPosition].htmlURL)
        }
    }

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