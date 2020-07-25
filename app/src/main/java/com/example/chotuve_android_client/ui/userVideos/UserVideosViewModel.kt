package com.example.chotuve_android_client.ui.userVideos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chotuve_android_client.data.VideoRepository
import com.example.chotuve_android_client.models.VideoList

class UserVideosViewModel {

    val TAG = "UserVideosViewModel"
    val videoRepository = VideoRepository()

    val _videos_list = MutableLiveData<VideoList>()
    val videos_list : LiveData<VideoList>
        get() = _videos_list

    fun getVideosFromSpecificUser(user_email : String) {
        videoRepository
            .getVideosForSpecificUserProfile(user_email, _videos_list)
    }
}