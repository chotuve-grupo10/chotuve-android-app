package com.example.chotuve_android_client.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.chotuve_android_client.services.UserProfileService
import io.reactivex.disposables.CompositeDisposable

class UserRepository {

    companion object {
        const val TAG = "UserRepository"
        private val userProfileService : UserProfileService = UserProfileService()
    }

    fun getFriendsInformation()  { // Esto recibirá _mutableVideos : MutableLiveData<UserList>
//        Log.d(VideoRepository.TAG, "Now, getting the videos from server..")
//        VideoRepository.homeListVideos.listVideos(
//            CompositeDisposable(),
//            {
//                if (it != null) {
//                    Log.d(
//                        VideoRepository.TAG, "La lista de videos de Home tiene este largo: " +
//                            it.size.toString())
//                }
//                _mutableVideos.value = it
//            },
//            {
//                it.printStackTrace()
//                Log.d(VideoRepository.TAG, "Error getting listVideos for HomeFragment")
//            }
//        )
    }

}