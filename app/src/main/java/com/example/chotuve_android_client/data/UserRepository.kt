package com.example.chotuve_android_client.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.chotuve_android_client.models.FriendsInformationList
import com.example.chotuve_android_client.services.UserProfileService
import io.reactivex.disposables.CompositeDisposable

class UserRepository {

    companion object {
        const val TAG = "UserRepository"
        private val userProfileService : UserProfileService = UserProfileService()
    }

    fun getFriendsInformation(user_email : String, _friends : MutableLiveData<FriendsInformationList>)  { // Esto recibirá _mutableVideos : MutableLiveData<UserList>
        Log.d(VideoRepository.TAG, "Now, getting friends from server..")
        userProfileService.listFriends(
            user_email,
            CompositeDisposable(),
            {
                if (it != null) {
                    Log.d(TAG, "Tengo esta cantidad de amigos: " + it.size.toString())
                }
                _friends.value = it
            },
            {
                it.printStackTrace()
                Log.d(VideoRepository.TAG, "Error getting users for UserProfile")
            }
        )
    }

}