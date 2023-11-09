package com.example.rcview.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rcview.RcViewAdapter
import com.example.rcview.UserAction
import com.example.rcview.databinding.FragmentUsersListBinding
import com.example.rcview.model.User

class UsersListFragment : Fragment() {
    private lateinit var binding: FragmentUsersListBinding
    private val viewModel: UsersListViewModel by viewModels { factory() }
    private lateinit var adapter: RcViewAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUsersListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = RcViewAdapter(object : UserAction {
            override fun onUserMove(user: User, moveBy: Int) {
                viewModel.moveUser(user, moveBy)
            }

            override fun onUserDelete(user: User) {
                viewModel.removeUser(user)
            }

            override fun onUserInfo(user: User) {
                TODO("Not yet implemented")
            }

            override fun onUserFire(user: User) {
                TODO("Not yet implemented")
            }

        })
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
        viewModel.users.observe(viewLifecycleOwner) {
            adapter.users = it
        }
    }
}