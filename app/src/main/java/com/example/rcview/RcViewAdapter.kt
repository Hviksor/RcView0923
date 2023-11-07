package com.example.rcview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.example.rcview.databinding.ItemUserBinding
import com.example.rcview.model.User

interface UserAction {
    fun onUserMove(user: User, moveBy: Int)
    fun onUserDelete(user: User)
    fun onUserInfo(user: User)
    fun onUserFire(user: User)

}

class UsersDiffCallback(
    private val oldUsers: List<User>,
    private val newUsers: List<User>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldUsers.size
    }

    override fun getNewListSize(): Int {
        return newUsers.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldUsers[oldItemPosition].id == newUsers[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldUsers[oldItemPosition] == newUsers[newItemPosition]
    }

}

class RcViewAdapter(
    private val userAction: UserAction
) : Adapter<RcViewAdapter.UsersViewHolder>(), View.OnClickListener {
    var users: List<User> = emptyList()
        set(newValue) {
            val diffCallback = UsersDiffCallback(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onClick(v: View) {
        val user = v.tag as User
        when (v.id) {
            R.id.moreImageViewButton -> showPopUpMenu(v)
            else -> userAction.onUserInfo(user)
        }
    }

    private fun showPopUpMenu(view: View) {
        val user = view.tag as User
        val context = view.context
        val position = users.indexOfFirst { it.id == user.id }
        val popupMenu = PopupMenu(context, view)
        popupMenu.menu.add(0, ID_MOVE_UP, Menu.NONE, context.getString(R.string.move_up)).apply {
            isEnabled = position > 0
        }
        popupMenu.menu.add(0, ID_MOVE_DOWN, Menu.NONE, context.getString(R.string.move_down))
            .apply {
                isEnabled = position < users.size - 1
            }
        popupMenu.menu.add(0, ID_REMOVE, Menu.NONE, context.getString(R.string.remove))
        if (user.company.isNotBlank()) {
            popupMenu.menu.add(0, ID_FIRE, Menu.NONE, context.getString(R.string.unemployed))
        }

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                ID_MOVE_UP -> {
                    userAction.onUserMove(user, -1)
                }

                ID_MOVE_DOWN -> {
                    userAction.onUserMove(user, 1)
                }

                ID_REMOVE -> {
                    userAction.onUserDelete(user)
                }

                ID_FIRE -> {
                    userAction.onUserFire(user)
                }
            }
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false)
        binding.moreImageViewButton.setOnClickListener(this)
        binding.root.setOnClickListener(this)
        return UsersViewHolder(binding)
    }

    override fun getItemCount(): Int = users.size
    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val user = users[position]
        val context = holder.itemView.context
        with(holder.binding) {
            holder.itemView.tag = user
            moreImageViewButton.tag = user
            userCompanyTextView.text =
                user.company.ifBlank { context.getString(R.string.unemployed) }
            userNameTextView.text = user.name
            if (user.photo.isNotBlank()) {
                Glide.with(photoImageView.context)
                    .load(user.photo)
                    .circleCrop()
                    .placeholder(R.drawable.ic_user_avatar)
                    .error(R.drawable.ic_user_avatar)
                    .into(photoImageView)
            } else {
                photoImageView.setImageResource(R.drawable.ic_user_avatar)
            }
        }
    }

    class UsersViewHolder(
        val binding: ItemUserBinding
    ) : RecyclerView.ViewHolder(binding.root)

    companion object {
        private const val ID_MOVE_UP = 0
        private const val ID_MOVE_DOWN = 1
        private const val ID_REMOVE = 2
        private const val ID_FIRE = 3
    }
}