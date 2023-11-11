package com.example.rcview.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rcview.UserNotFoundException
import com.example.rcview.model.User
import com.example.rcview.model.UserDetails
import com.example.rcview.model.UserListener
import com.example.rcview.model.UserService

class UsersDetailViewModel(
    private val userService: UserService
) : ViewModel() {
    private val _userDetail = MutableLiveData<UserDetails>()
    var userDetail: LiveData<UserDetails> = _userDetail


    fun loadUserDetail(userId: Long) {
        if (_userDetail.value != null) return
        try {
            _userDetail.value = userService.getUserDetail(userId)
        } catch (e: UserNotFoundException) {
            e.printStackTrace()
        }
    }

    fun deleteUser() {
        userDetail.value?.let { userService.deleteUser(it.user) }
    }


}