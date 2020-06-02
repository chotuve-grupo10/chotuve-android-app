package com.example.chotuve_android_client.ui.playVideo

import android.net.Uri
import android.util.Log
import android.widget.VideoView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chotuve_android_client.data.model.Video

class PlayVideoViewModel (private val video: Video) : ViewModel() {
    // Acá haré algo con el video.

    private val _text = MutableLiveData<String>().apply {
        value = video.title
    }
    val text: LiveData<String> = _text
//    private val _videoView = MutableLiveData<VideoView>().apply {
//        value =
//    }
     private val videoView = VideoView()
        val uri = Uri.parse(video.url)
        _videoView.setVideoUri(uri)
        // setear visibility de una progressBar acá?

}
