package com.example.chotuve_android_client.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.chotuve_android_client.models.FriendsInformationList
import com.example.chotuve_android_client.services.SearchUserService
import com.example.chotuve_android_client.services.UserProfileService
import io.reactivex.disposables.CompositeDisposable

class UserRepository {

    companion object {
        const val TAG = "UserRepository"
        private val userProfileService : UserProfileService = UserProfileService()
        private val searchUsersService : SearchUserService = SearchUserService()
    }

    fun getFriendsInformation(user_email : String, _friends : MutableLiveData<FriendsInformationList>)  {
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

    fun getUsers(filter : String, _users : MutableLiveData<FriendsInformationList>) {
        Log.d(VideoRepository.TAG, "Now, getting users filtered from server..")
        searchUsersService.getUsersFiltered(
            filter,
            CompositeDisposable(),
            {
                if (it != null) {
                    Log.d(TAG, "Tengo esta cantidad de usuarios: " + it.size.toString())
                }
                _users.value = it
            },
            {
                it.printStackTrace()
                Log.d(VideoRepository.TAG, "Error getting users for SearchFragment")
            }
        )
    }

}