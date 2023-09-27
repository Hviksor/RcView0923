package com.example.rcview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rcview.databinding.ActivityMainBinding
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
        adapter = RcViewAdapter()
        binding.rcView.layoutManager = LinearLayoutManager(this)
        binding.rcView.adapter = adapter
        userService.addListener(userListener)
    }

    private val userListener: UserListener = {
        adapter.users = it
    }

    override fun onDestroy() {
        userService.removeListener(userListener)
        super.onDestroy()

    }
}