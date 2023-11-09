package com.example.rcview.screens

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rcview.App
import com.example.rcview.model.UserService

class ViewModelFactory(
    private val app: App
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass) {
            UsersListViewModel::class.java -> {
                UsersListViewModel(app.userService)
            }

            else -> {
                throw IllegalStateException("Unknown ViewModel")
            }
        }
        return viewModel as T
    }
}

fun Fragment.factory() = ViewModelFactory(requireContext().applicationContext as App)