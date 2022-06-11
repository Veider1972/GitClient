package ru.veider.gitclient.ui.gitusers

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.veider.gitclient.R
import ru.veider.gitclient.app
import ru.veider.gitclient.databinding.FragmentUsersBinding
import ru.veider.gitclient.domain.entity.GitUsersEntity
import ru.veider.gitclient.presenter.gitusers.GitUsersContract
import ru.veider.gitclient.presenter.gitusers.GitUsersPresenter
import ru.veider.gitclient.ui.TAG

class GitUsersFragment : Fragment(), GitUsersContract.View {

    companion object {
        fun newInstance() = GitUsersFragment()
    }

    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!
    private val adapter by lazy { GitUsersAdapter(this) }
    private lateinit var presenter: GitUsersContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = GitUsersPresenter(app.gitUsersRepository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_users, container, false)
        _binding = FragmentUsersBinding.bind(view)
        retainInstance = true
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attach(this)
        initViews()
    }

    private fun initViews() {
        presenter.onRefresh()
        initRecyclerView()
        //showProgress(false)
    }

    private fun initRecyclerView() {
        binding.usersRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.usersRecyclerView.adapter = adapter
    }

    override fun showUsers(users: List<GitUsersEntity>) {
        adapter.setData(users)
    }

    override fun showError(throwable: Throwable) {
        Toast.makeText(requireContext(), throwable.message, Toast.LENGTH_SHORT).show()
    }

    override fun showProgress(showingProgress: Boolean) {
        binding.apply {
            progressBar.visibility = if (showingProgress) View.VISIBLE else View.GONE
            usersRecyclerView.visibility = if (showingProgress) View.GONE else View.VISIBLE
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detach()
        _binding = null
    }
}