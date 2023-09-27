package com.example.rcview

import android.app.Application
import com.example.rcview.model.UserService

class App : Application() {
    val userService = UserService()

}