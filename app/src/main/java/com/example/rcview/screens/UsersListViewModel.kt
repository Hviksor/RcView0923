package com.example.rcview.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rcview.model.User
import com.example.rcview.model.UserListener
import com.example.rcview.model.UserService

class UsersListViewModel(
    private val userService: UserService
) : ViewModel() {
    private val _users = MutableLiveData<List<User>>()
    var users: LiveData<List<User>> = _users
    private val listener: UserListener = {
        _users.value = it
    }
    init {
        loadUsers()
    }
    private fun loadUsers() {
        userService.addListener(listener)
    }
    fun removeUser(user: User) {
        userService.deleteUser(user)
    }
    fun moveUser(user: User, moveBy: Int) {
        userService.moveUser(user, moveBy)
    }
    override fun onCleared() {
        super.onCleared()
        userService.removeListener(listener)
    }
}