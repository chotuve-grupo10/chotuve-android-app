package com.example.chotuve_android_client.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.chotuve_android_client.models.VideoList
import com.example.chotuve_android_client.services.HomeListVideosService
import com.example.chotuve_android_client.services.UsersListVideosService
import com.example.chotuve_android_client.tools.TokenHolder
import io.reactivex.disposables.CompositeDisposable

class VideoRepository() {

    companion object {
        const val TAG = "VideoRepository"
        private val homeListVideos : HomeListVideosService = HomeListVideosService()
        private val usersLIstVideos : UsersListVideosService = UsersListVideosService()
    }

    fun getVideosForHome(_mutableVideos : MutableLiveData<VideoList>)  {
        Log.d(TAG, "Now, getting the videos from server..")
        homeListVideos.listVideos(
            TokenHolder.username,
            CompositeDisposable(),
            {
                if (it != null) {
                    Log.d(TAG, "La lista de videos de Home tiene este largo: " +
                            it.size.toString())
                }
                _mutableVideos.value = it
            },
            {
                it.printStackTrace()
                Log.d(TAG, "Error getting listVideos for HomeFragment")
            }
        )
    }

    fun getVideosForSpecificUserProfile(email : String, _mutableVideos : MutableLiveData<VideoList>) {
        Log.d(TAG, "Now, getting  users ${email} videos from server..")
        usersLIstVideos
            .listVideos(
                email,
                TokenHolder.appServerToken,
                CompositeDisposable(),
                {
                    if (it != null) {
                        Log.d(TAG, "La lista de videos de Home tiene este largo: " +
                                it.size.toString())
                        _mutableVideos.value = it
                    }
                },
                {
                    it.printStackTrace()
                    Log.e(TAG, "Error getting listVideos for UserVideosViewModel")
                }
            )
    }

}