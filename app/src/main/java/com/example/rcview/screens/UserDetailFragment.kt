package com.example.rcview.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.rcview.databinding.FragmentUserDetailsBinding
import com.example.rcview.databinding.FragmentUsersListBinding

class UserDetailFragment : Fragment() {
    private lateinit var binding: FragmentUserDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

}