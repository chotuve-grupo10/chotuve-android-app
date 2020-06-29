package com.example.chotuve_android_client.tools.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.chotuve_android_client.models.FriendsInformationList
import com.example.chotuve_android_client.databinding.RecyclerviewUsersBinding
import com.example.chotuve_android_client.R
import com.example.chotuve_android_client.data.VideoRepository
import com.example.chotuve_android_client.models.RequestFriendshipResponse
import com.example.chotuve_android_client.services.RequestFriendshipService
import com.example.chotuve_android_client.tools.TokenHolder
import io.reactivex.disposables.CompositeDisposable
import retrofit2.HttpException
import retrofit2.Response

class UsersAdapter(
    val users : FriendsInformationList
) : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    val TAG = "Users Adapter"

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
                Log.d(TAG, "Error sending friendship request")
                Log.d(TAG, it.localizedMessage)
                if(it is HttpException){
                    // esto devuelve null
                    val response : Response<RequestFriendshipResponse> = it.response() as Response<RequestFriendshipResponse>
                    Log.d(TAG, response.body()?.messageResult.toString())
                }
                val ad = AlertDialog.Builder(view.context)
                ad.setMessage(it.message.toString())
                ad.show()
            }
        )

    }

    inner class UserViewHolder(
        val recyclerviewUsersBinding : RecyclerviewUsersBinding
    ) : RecyclerView.ViewHolder(recyclerviewUsersBinding.root)
}