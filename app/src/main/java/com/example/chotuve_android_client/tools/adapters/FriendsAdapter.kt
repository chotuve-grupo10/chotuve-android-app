package com.example.chotuve_android_client.tools.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.chotuve_android_client.models.FriendsInformationList
import com.example.chotuve_android_client.databinding.RecyclerviewFriendsBinding
import com.example.chotuve_android_client.R

class FriendsAdapter(
    val users : FriendsInformationList
) : RecyclerView.Adapter<FriendsAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.recyclerview_friends,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.recyclerviewFriendsBinding.user = users[position]
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
        val recyclerviewFriendsBinding : RecyclerviewFriendsBinding
    ) : RecyclerView.ViewHolder(recyclerviewFriendsBinding.root)
}