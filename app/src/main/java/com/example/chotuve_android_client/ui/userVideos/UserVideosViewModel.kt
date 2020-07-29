package com.example.chotuve_android_client.ui.userVideos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chotuve_android_client.data.UserRepository
import com.example.chotuve_android_client.data.VideoRepository
import com.example.chotuve_android_client.models.VideoList

class UserVideosViewModel {

    val TAG = "UserVideosViewModel"
    private val videoRepository = VideoRepository()
    private val userRepository: UserRepository = UserRepository()

    val _text = MutableLiveData<String>().apply {
        this.value = "Bienvenido al perfil de "
    }
    val text : LiveData<String>
        get() = _text

    private val _URL = MutableLiveData<String>().apply {
        this.value = ""
    }
    val URL : LiveData<String>
        get() = _URL

    val _videos_list = MutableLiveData<VideoList>()
    val videos_list : LiveData<VideoList>
        get() = _videos_list

    fun getVideosFromSpecificUser(user_email : String) =
        videoRepository
            .getVideosForSpecificUserProfile(user_email, _videos_list)


    fun getUserProfilePicture(user_email: String) =
        userRepository.getOthersUserProfilePicture(user_email, _URL)


}