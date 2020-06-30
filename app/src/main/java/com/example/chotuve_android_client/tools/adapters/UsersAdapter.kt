package com.example.chotuve_android_client.tools.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.chotuve_android_client.models.UsersInformationList
import com.example.chotuve_android_client.databinding.RecyclerviewUsersBinding
import com.example.chotuve_android_client.R
import com.example.chotuve_android_client.services.RequestFriendshipService
import com.example.chotuve_android_client.tools.TokenHolder
import com.example.chotuve_android_client.tools.error_handlers.ServerMessageHttpExceptionHandler
import io.reactivex.disposables.CompositeDisposable
import retrofit2.HttpException


class UsersAdapter(
    val users : UsersInformationList
) : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    val TAG = "Users Adapter"
    val FRIENDSHIP_REQUEST = "Friendship_request"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.recyclerview_users,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.recyclerviewUsersBinding.user = users[position]
        holder.recyclerviewUsersBinding.buttonSeeProfile.setOnClickListener { view ->
            requestFriendship(view,
                TokenHolder.username,
                users[position].email.toString())
        }

    }

    override fun getItemCount() = users.size

    fun requestFriendship(view : View, user_email: String, new_friends_email : String) {
        Log.d(TAG, "Sending friendship request from ${user_email} to ${new_friends_email}")

        val requestFriendshipService = RequestFriendshipService()
        requestFriendshipService.requestFriendship(
            user_email,
            new_friends_email,
            CompositeDisposable(),
            {
                val ad = AlertDialog.Builder(view.context)
                ad.setMessage("La solicitud de amistad fue enviada con éxito!")
                ad.show()
            },
            {
                it.printStackTrace()
                Log.d(TAG, "Error sending friendship request: ${it.localizedMessage}")
                if(it is HttpException){
                    val error = ServerMessageHttpExceptionHandler(it, FRIENDSHIP_REQUEST)
                    val ad = AlertDialog.Builder(view.context)
                    ad.setMessage(error.message)
                    ad.show()
                }
            }
        )
    }

    inner class UserViewHolder(
        val recyclerviewUsersBinding : RecyclerviewUsersBinding
    ) : RecyclerView.ViewHolder(recyclerviewUsersBinding.root)
}