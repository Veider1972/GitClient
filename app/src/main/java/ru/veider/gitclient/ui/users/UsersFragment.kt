package ru.veider.gitclient.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import ru.veider.gitclient.App
import ru.veider.gitclient.R
import ru.veider.gitclient.databinding.FragmentUsersBinding
import ru.veider.gitclient.domain.entity.UserEntity
import ru.veider.gitclient.ui.user.UserFragment

class UsersFragment : Fragment() {

    private val TAG = "App ${this::class.java.simpleName} : ${this.hashCode()}"

    companion object {
        fun newInstance() = UsersFragment()
    }

    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!
    private val adapter by lazy {
        UsersAdapter {
            viewModel.openUserPage(it)
        }
    }
    private lateinit var viewModel: UsersContract.ViewModel
    private val viewModelDisposable = CompositeDisposable()
    private val observerDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        viewModel = ViewModelProvider(this, UsersViewModelFactory.getInstance(App.cachedUsersRepository))[UsersViewModel::class.java]
        observerDisposable.addAll(
            viewModel.userPageObserver.subscribeBy { openUserPage(it) })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_users, container, false)
        _binding = FragmentUsersBinding.bind(view)
        binding.next.onClickObserver.subscribe{
            if (it){
                viewModel.nextUsers()
            }
        }
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
        viewModelDisposable.addAll(
            viewModel.progressObserver.subscribe { showProgress(it) },
            viewModel.usersObserver.subscribe { showUsers(it) },
            viewModel.errorObserver.subscribe { showError(it) })
    }

    private fun openUserPage(userEntity: UserEntity) {
        parentFragmentManager
            .beginTransaction()
            .add(R.id.activity_main_container, UserFragment.newInstance(userEntity))
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .addToBackStack("user")
            .commit()
    }

    private fun initRecyclerView() {
        binding.usersRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.usersRecyclerView.adapter = adapter
    }

    private fun showUsers(users: List<UserEntity>) {
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
        viewModelDisposable.dispose()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        observerDisposable.dispose()
    }
}