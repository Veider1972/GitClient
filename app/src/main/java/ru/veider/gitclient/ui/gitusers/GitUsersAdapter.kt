package ru.veider.gitclient.ui.gitusers

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.veider.gitclient.R
import ru.veider.gitclient.domain.entity.GitUsersEntity
import ru.veider.gitclient.ui.gituser.GitUserFragment

class GitUsersAdapter(
    val gitUsersFragment: GitUsersFragment
) : RecyclerView.Adapter<GitUsersViewHolder>() {

    init {
        setHasStableIds(true)
    }

    private var users = mutableListOf<GitUsersEntity>()

    override fun getItemId(position: Int) = users[position].id

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GitUsersViewHolder(parent).apply {
        itemView.setOnClickListener {
            gitUsersFragment
                .parentFragmentManager
                .beginTransaction()
                .replace(R.id.activity_main_container, GitUserFragment.newInstance(users[adapterPosition].htmlURL))
                .addToBackStack("")
                .commit()
        }
    }

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