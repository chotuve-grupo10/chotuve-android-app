package com.example.chotuve_android_client.tools.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.chotuve_android_client.models.UsersInformationList
import com.example.chotuve_android_client.databinding.RecyclerviewNotificationsBinding
import com.example.chotuve_android_client.R
import com.example.chotuve_android_client.models.AcceptFriend
import com.example.chotuve_android_client.services.RespondFriendshipService
import com.example.chotuve_android_client.tools.TokenHolder
import com.example.chotuve_android_client.tools.error_handlers.ServerMessageHttpExceptionHandler
import io.reactivex.disposables.CompositeDisposable
import retrofit2.HttpException


class RequestsAdapter(
    var users : UsersInformationList
) : RecyclerView.Adapter<RequestsAdapter.UserViewHolder>() {

    val TAG = "RequestsAdapter"
    val ACCEPT_KEY = "Accept_friendship_request"
    val ACCEPT_SUCCESSFULL_MESSAGE = "Usuario aceptado con éxito. Ya son amigos!"
    val REJECT_KEY = "Reject_friendship_request"
    val REJECT_SUCCESSFULL_MESSAGE = "La solicitud de amistad fue rechazada con éxito"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.recyclerview_notifications,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.recyclerviewUsersBinding.user = users[position]
        holder.recyclerviewUsersBinding.buttonAcceptRequest.setOnClickListener { view ->
            Log.d(TAG, "Accepting friendship...")
            respondFriendshipRequest(view, TokenHolder.username, users[position].email.toString(),
                AcceptFriend(response=true),
                ACCEPT_KEY, ACCEPT_SUCCESSFULL_MESSAGE)
            removeItem(position)
            notifyItemRemoved(position)

        }
        holder.recyclerviewUsersBinding.buttonRejectRequest.setOnClickListener { view ->
            Log.d(TAG, "Rejecting friendship...")
            respondFriendshipRequest(view, TokenHolder.username, users[position].email.toString(),
                AcceptFriend(response=false),
                REJECT_KEY, REJECT_SUCCESSFULL_MESSAGE)
            removeItem(position)
            notifyItemRemoved(position)
        }
    }

    override fun getItemCount() = users.size

    fun respondFriendshipRequest(
        view : View,
        user_email: String,
        new_friends_email : String,
        responseBody : AcceptFriend,
        responseKey : String,
        successfullMessage : String
    ) {
        Log.d(TAG, "Responding friendship request from ${user_email} to ${new_friends_email}")
        Log.d(TAG, "¿It is true that i'm accepting friendship? ${responseBody.response}")
        RespondFriendshipService().respondFriendshipRequest(
            user_email,
            new_friends_email,
            responseBody,
            CompositeDisposable(),
            {
                val ad = AlertDialog.Builder(view.context)
                ad.setMessage(successfullMessage)
                ad.show()
            },
            {
                it.printStackTrace()
                Log.d(TAG, "Error sending friendship request: ${it.localizedMessage}")
                if(it is HttpException){
                    val error = ServerMessageHttpExceptionHandler(it, responseKey)
                    val ad = AlertDialog.Builder(view.context)
                    ad.setMessage(error.message)
                    ad.show()
                }
            }
        )
    }

    fun removeItem(position: Int) {
        users = users.minus(users[position])
        notifyDataSetChanged()
    }

    inner class UserViewHolder(
        val recyclerviewUsersBinding : RecyclerviewNotificationsBinding
    ) : RecyclerView.ViewHolder(recyclerviewUsersBinding.root)
}