package com.example.chotuve_android_client.ui.uploadVideo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UploadVideoViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "In here you can upload your video"
    }
    val text: LiveData<String> = _text
}