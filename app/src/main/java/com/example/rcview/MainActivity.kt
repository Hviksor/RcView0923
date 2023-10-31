package com.example.rcview

import android.net.wifi.p2p.WifiP2pManager.ActionListener
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
        adapter = RcViewAdapter(actionListener)
        binding.rcView.layoutManager = LinearLayoutManager(this)
        binding.rcView.adapter = adapter
        userService.addListener(usersListener)
    }

    private val actionListener = object : UserActionListener {

        override fun onMoveUser(user: User, moveTo: Int) {
            userService.moveUser(user, moveTo)
        }

        override fun onDeleteUser(user: User) {
            userService.deleteUser(user)
        }

        override fun onUserDetails(user: User) {
            Toast.makeText(this@MainActivity, "User: ${user.name}", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroy() {
        userService.removeListener(usersListener)
        super.onDestroy()

    }

    private val usersListener: UserListener = {
        adapter.users = it
    }
}