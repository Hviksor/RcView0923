package com.example.rcview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.rcview.databinding.ActivityMainBinding
import com.example.rcview.model.UserListener
import com.example.rcview.model.UserService
import com.example.rcview.screens.UserDetailFragment
import com.example.rcview.screens.UsersListFragment

class MainActivity : AppCompatActivity(), Navigator {
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

    override fun back() {
        onBackPressed()
    }

    override fun userDetail(userId: Long) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, UserDetailFragment.getInstance(userId))
            .addToBackStack(null)
            .commit()
    }

    override fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}