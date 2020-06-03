package com.example.chotuve_android_client.ui.playVideo

import android.net.Uri
import android.util.Log
import android.widget.VideoView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chotuve_android_client.data.model.Video
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.lang.Exception

// TODO: env var this
val APPSPOT_URL = "gs://chotuve-android-app.appspot.com";


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
}
