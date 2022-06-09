package ru.veider.gitclient.ui.gitusers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.veider.gitclient.R
import ru.veider.gitclient.app
import ru.veider.gitclient.databinding.FragmentMainBinding
import ru.veider.gitclient.domain.entity.GitUsersEntity
import ru.veider.gitclient.domain.repository.GitUsersRepository

class GitUsersFragment : Fragment() {

    companion object {
        fun newInstance() = GitUsersFragment()
    }

    private lateinit var binding : FragmentMainBinding
    private val adapter = GitUsersAdapter()
    private val repository: GitUsersRepository by lazy { app.gitUsersRepository }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        binding = FragmentMainBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        loadData()
        initRecyclerView()
    }

    private fun loadData() {
        showProgress(true)
        repository.getUsers(
            onSuccess = {
                showProgress(false)
                onDataLoaded(it)
            },
            onError = {
                showProgress(false)
                onError(it)
            }
        )
    }

    private fun onDataLoaded(data: List<GitUsersEntity>) {
        adapter.setData(data)
    }

    private fun onError(throwable: Throwable) {
        Toast.makeText(requireContext(), throwable.message, Toast.LENGTH_SHORT).show()
    }

    private fun initRecyclerView() {
        binding.usersRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.usersRecyclerView.adapter = adapter
    }

    private fun showProgress(isVisible: Boolean) {
        binding.progressBar.isVisible = isVisible
        binding.usersRecyclerView.isVisible = !isVisible
    }
}