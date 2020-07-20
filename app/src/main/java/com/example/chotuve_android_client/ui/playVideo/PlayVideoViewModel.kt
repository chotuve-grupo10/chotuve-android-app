package com.example.chotuve_android_client.ui.playVideo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chotuve_android_client.models.Video

class PlayVideoViewModel (private val video: Video) : ViewModel() {

    private val _title = MutableLiveData<String>().apply {
        this.value = video.title
    }
    val title: LiveData<String> = _title

    private val _url = MutableLiveData<String>().apply {
        this.value = video.url
    }
    val url: LiveData<String> = _url

    init {
        Log.d("playvid", "Soy el PlayVideoViewModel y el video es este "
        + video.title + ". La URL es esta" + video.url)
    }

    fun likeVideo() {

    }
}
