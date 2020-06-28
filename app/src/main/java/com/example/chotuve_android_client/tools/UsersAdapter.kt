package com.example.chotuve_android_client.tools

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.chotuve_android_client.models.FriendsInformationList
import com.example.chotuve_android_client.databinding.RecyclerviewUsersBinding
import com.example.chotuve_android_client.R

class UsersAdapter(
    val users : FriendsInformationList
) : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersAdapter.UserViewHolder {
        return UserViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.recyclerview_users,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UsersAdapter.UserViewHolder, position: Int) {
        holder.recyclerviewUsersBinding.user = users[position]
        // setOnClickListener
        // Here I can add clickListeners to different elements in coso
//        holder.recyclerviewVideoBinding.videoTitle.setOnClickListener { view ->
//            val ad = AlertDialog.Builder(view.context)
//            ad.setMessage("You clicked on " + videos[position].title)
//            ad.show()
//        }

    }

    override fun getItemCount() = users.size

    inner class UserViewHolder(
        val recyclerviewUsersBinding : RecyclerviewUsersBinding
    ) : RecyclerView.ViewHolder(recyclerviewUsersBinding.root)
}