package com.example.rcview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rcview.databinding.ActivityMainBinding
import com.example.rcview.databinding.FragmentUsersListBinding
import com.example.rcview.model.User
import com.example.rcview.model.UserListener
import com.example.rcview.model.UserService
import com.example.rcview.screens.UsersListFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, UsersListFragment())
                .commit()
        }


    }

}