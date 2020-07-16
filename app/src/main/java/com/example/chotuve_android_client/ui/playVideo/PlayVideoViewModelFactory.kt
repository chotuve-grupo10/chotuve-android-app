package com.example.chotuve_android_client.ui.playVideo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chotuve_android_client.models.VideoListInner

class PlayVideoViewModelFactory(
    private val video: VideoListInner
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlayVideoViewModel::class.java)) {
            return PlayVideoViewModel(video) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class. Not a PlayVideoViewModel")
    }

}