package com.example.rcview.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.rcview.R
import com.example.rcview.databinding.FragmentUserDetailsBinding

class UserDetailFragment : Fragment() {
    private lateinit var binding: FragmentUserDetailsBinding
    private val viewModel: UsersDetailViewModel by viewModels { factory() }
    override fun onStart() {
        super.onStart()
        val userId = requireArguments().getLong(ARG_USER_ID)
        viewModel.loadUserDetail(userId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.userDetail.observe(viewLifecycleOwner) {
            binding.userDetailsTextView.text = it.details
            binding.userNameTextView.text = it.user.name
            if (it.user.photo.isNotBlank()) {
                Glide.with(this)
                    .load(it.user.photo)
                    .centerCrop()
                    .into(binding.photoImageView)
            } else {
                Glide.with(this)
                    .load(R.drawable.ic_user_avatar)
                    .centerCrop()
                    .into(binding.photoImageView)
            }
        }
        binding.deleteButton.setOnClickListener {
            viewModel.userDetail
            navigator().toast(getString(R.string.user_has_been_deleted))
            navigator().back()

        }

    }



    companion object {
        private const val ARG_USER_ID = "ARG_USER_ID"
        fun getInstance(userId: Long) = UserDetailFragment().apply {
            arguments = bundleOf(ARG_USER_ID to userId)
        }
    }

}