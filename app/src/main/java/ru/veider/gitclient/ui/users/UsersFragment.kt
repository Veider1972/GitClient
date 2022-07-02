package ru.veider.gitclient.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ru.veider.gitclient.R
import ru.veider.gitclient.databinding.FragmentUsersBinding
import ru.veider.gitclient.domain.entity.UserEntity
import ru.veider.gitclient.ui.user.UserFragment

class UsersFragment : Fragment() {

    private val TAG = "App ${this::class.java.simpleName} : ${this.hashCode()}"

    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!
    private val adapter by lazy {
        UsersAdapter {
            viewModel.openUserPage(it)
        }
    }
    private val viewModel: UsersViewModel by sharedViewModel()
    private val viewModelDisposable = CompositeDisposable()
    private val observerDisposable = CompositeDisposable()
    private val bindingDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observerDisposable.add(
            viewModel.userPageObserver.subscribeBy {
                this.view?.apply {
                    if (this.isVisible) openUserPage(it)
                }
            })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_users, container, false)
        _binding = FragmentUsersBinding.bind(view)
        bindingDisposable.add(
            binding.next.onClickObserver.subscribeBy(
                onNext = {
                    if (it) {
                        viewModel.nextUsers()
                    }
                }
            )
        )
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
            .add(R.id.activity_main_container, UserFragment().apply {
                this.arguments = Bundle().apply {
                    putParcelable(UserEntity::class.java.toString(), userEntity)
                }
            })
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .addToBackStack(null)
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
        bindingDisposable.dispose()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        observerDisposable.dispose()
    }
}