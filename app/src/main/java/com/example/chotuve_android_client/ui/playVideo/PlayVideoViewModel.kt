package com.example.chotuve_android_client.ui.playVideo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chotuve_android_client.data.model.Video

class PlayVideoViewModel (private val video: Video) : ViewModel() {
    // Acá haré algo con el video.
    init {
        Log.d("playvid", "Soy el PlayVideoViewModel y el video es este " + video.title)
    }


}
