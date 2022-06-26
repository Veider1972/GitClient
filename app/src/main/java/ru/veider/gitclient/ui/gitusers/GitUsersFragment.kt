package ru.veider.gitclient.ui.gitusers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ru.veider.gitclient.R
import ru.veider.gitclient.app
import ru.veider.gitclient.databinding.FragmentUsersBinding
import ru.veider.gitclient.domain.entity.GitUsersData
import ru.veider.gitclient.ui.gituser.GitUserFragment

class GitUsersFragment : Fragment(), GitUsersAdapter.OnItemClick {

    private val TAG = "App ${this::class.java.simpleName} : ${this.hashCode()}"

    companion object {
        fun newInstance() = GitUsersFragment()
    }

    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!
    private val adapter by lazy { GitUsersAdapter(this) }
    private lateinit var viewModel: GitUsersContract.ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, GitUsersViewModelFactory.getInstance(app.gitUsersRepository))[GitUsersViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_users, container, false)
        _binding = FragmentUsersBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initViewModel()
    }

    private fun initViews() {
        initRecyclerView()
    }

    private fun initViewModel() {
        viewModel.progressLiveData.observe(viewLifecycleOwner) { showProgress(it) }
        viewModel.usersLiveData.observe(viewLifecycleOwner) { showUsers(it) }
        viewModel.errorLiveData.observe(viewLifecycleOwner) { showError(it) }
        viewModel.userPageLiveData.observe(viewLifecycleOwner){openUserPage(it)}
    }

    private fun openUserPage(url: String) {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.activity_main_container, GitUserFragment.newInstance(url))
            .addToBackStack("")
            .commit()
    }

    private fun initRecyclerView() {
        binding.usersRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.usersRecyclerView.adapter = adapter
    }

    private fun showUsers(users: List<GitUsersData>) {
        adapter.setData(users)
    }

    private fun showError(throwable: Throwable) {
        Toast.makeText(requireContext(), throwable.message, Toast.LENGTH_SHORT).show()
    }

    private fun showProgress(showingProgress: Boolean) {
        binding.apply {
            progressBar.visibility = if (showingProgress) View.VISIBLE else View.GONE
            usersRecyclerView.visibility = if (showingProgress) View.GONE else View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onUserSelect(url: String) {
        viewModel.openUserPage(url)
    }
}