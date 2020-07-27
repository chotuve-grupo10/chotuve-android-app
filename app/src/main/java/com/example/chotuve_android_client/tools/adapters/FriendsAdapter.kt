package com.example.chotuve_android_client.tools.adapters

import android.app.AlertDialog
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.chotuve_android_client.databinding.RecyclerviewFriendsBinding
import com.example.chotuve_android_client.models.UsersInformationList
import com.example.chotuve_android_client.services.DeleteFriendshipService
import com.example.chotuve_android_client.tools.TokenHolder
import com.example.chotuve_android_client.tools.error_handlers.ServerMessageHttpExceptionHandler
import com.example.chotuve_android_client.ui.messaging.MessagingActivity
import com.example.chotuve_android_client.ui.userVideos.UserVideosActivity
import com.squareup.picasso.Picasso
import io.reactivex.disposables.CompositeDisposable
import retrofit2.HttpException


class FriendsAdapter(
    var users : UsersInformationList
) : RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder>() {

    val TAG = "FriendsAdapter"
    val FRIENDSHIP_DELETE = "Delete_friendship_request"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsViewHolder {
        return FriendsViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                com.example.chotuve_android_client.R.layout.recyclerview_friends,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FriendsViewHolder, position: Int) {
        holder.recyclerviewFriendsBinding.user = users[position]

        val profilePicture = users[position].profilePicture
        if (profilePicture != "") {
            Picasso
                .get()
                .load(profilePicture) // https://matthewjameskirk.co.uk/Images/video.jpg
                .into(holder.recyclerviewFriendsBinding.profilePicture)
        }
        // Here I can add clickListeners to different elements in coso
        holder.recyclerviewFriendsBinding.buttonDeleteFriend.setOnClickListener { view ->
//            val ad = AlertDialog.Builder(view.context)
            Log.d(TAG, "User about to remove this friend " + users[position].fullName)
            deleteFriendship(view,
                TokenHolder.username,
                users[position].email.toString(),
                position
            )
        }

        holder.recyclerviewFriendsBinding.buttonMessageFriend.setOnClickListener { view ->
//            val ad = AlertDialog.Builder(view.context)
            Log.d(TAG, "User about to chat with this friend " + users[position].fullName)
            val intent: Intent = Intent(view.context, MessagingActivity::class.java)
            intent.putExtra("user_to_message_id", users[position].email)
            intent.putExtra("user_to_message_full_name", users[position].fullName)
            view.context.startActivity(intent)
        }

        holder.recyclerviewFriendsBinding.buttonSeeProfile.setOnClickListener { view ->
            Log.d(TAG, "User about to see this profile " + users[position].email)
            val intent: Intent = Intent(view.context, UserVideosActivity::class.java)
            intent.putExtra("user_email", users[position].email)
            intent.putExtra("user_full_name", users[position].fullName)
            view.context.startActivity(intent)

//            val activity = view.context as AppCompatActivity
//            val myFragment: Fragment = UserVideosFragment.newInstance(username=users[position].email.toString())
//            activity.supportFragmentManager.beginTransaction()
//                .replace(com.example.chotuve_android_client.R.id.nav_host_fragment, myFragment)
//                .addToBackStack(null)
//                .commit()
        }
    }

    override fun getItemCount() = users.size

    fun removeItem(position: Int) {
        users = users.minus(users[position])
        notifyDataSetChanged()
    }

    fun deleteFriendship(view: View, user_email : String, friends_email : String, position: Int) {
        val deleteFriendshipService = DeleteFriendshipService()
        deleteFriendshipService.deleteFriendship(
            user_email,
            friends_email,
            CompositeDisposable(),
            {
                val ad = AlertDialog.Builder(view.context)
                ad.setMessage("Usuario " + friends_email + " eliminado de la lista de amigos con éxito")
                ad.show()
                removeItem(position)
                notifyItemRemoved(position)
//                notifyItemRangeChanged(position, itemCount-1)
//                notifyDataSetChanged()
            },
            {
                it.printStackTrace()
                Log.d(TAG, "Error sending friendship request: ${it.localizedMessage}")
                if (it is HttpException) {
                    val error = ServerMessageHttpExceptionHandler(it, FRIENDSHIP_DELETE)
                    val ad = androidx.appcompat.app.AlertDialog.Builder(view.context)
                    ad.setMessage(error.message)
                    ad.show()
                }
            }
        )
    }

    inner class FriendsViewHolder(
        val recyclerviewFriendsBinding : RecyclerviewFriendsBinding
    ) : RecyclerView.ViewHolder(recyclerviewFriendsBinding.root)
}