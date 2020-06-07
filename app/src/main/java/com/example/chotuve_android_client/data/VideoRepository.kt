package com.example.chotuve_android_client.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.chotuve_android_client.models.VideoList
import com.example.chotuve_android_client.services.HomeListVideosService
import io.reactivex.disposables.CompositeDisposable

class VideoRepository() {

    companion object {
        const val TAG = "VideoRepository"
        private val homeListVideos : HomeListVideosService = HomeListVideosService()
    }

    fun getVideosForHome(_mutableVideos : MutableLiveData<VideoList>)  {
        Log.d(TAG, "Now, getting the videos from server..")
        homeListVideos.listVideos(
            CompositeDisposable(),
            {
                Log.d(TAG, "This was somehow successfull....!")
                if (it != null) {
                    Log.d(TAG, "Este largo tiene la lista " +
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

}