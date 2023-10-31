package com.example.rcview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rcview.databinding.ActivityMainBinding
import com.example.rcview.model.User
import com.example.rcview.model.UserListener
import com.example.rcview.model.UserService

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: RcViewAdapter
    private val userService: UserService
        get() = (applicationContext as App).userService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = RcViewAdapter(object : UserAction {
            override fun onUserMove(user: User, moveBy: Int) {
                userService.moveUser(user, moveBy)
            }

            override fun onUserDelete(user: User) {
                userService.deleteUser(user)
            }

            override fun onUserInfo(user: User) {
                Toast.makeText(this@MainActivity, "User: $user", Toast.LENGTH_SHORT).show()
            }
        })
        binding.rcView.layoutManager = LinearLayoutManager(this)
        binding.rcView.adapter = adapter
        userService.addListener(usersListener)
    }

    private val usersListener: UserListener = {
        adapter.users = it
    }

    override fun onDestroy() {
        super.onDestroy()
        userService.removeListener(usersListener)

    }
}