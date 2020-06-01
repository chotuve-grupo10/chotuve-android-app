package com.example.chotuve_android_client.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chotuve_android_client.data.VideoRepository
import com.example.chotuve_android_client.data.model.Video

class HomeViewModel(
    private val repository: VideoRepository
) : ViewModel() {

    // Tengo mi objeto MutableLiveData pero expongo hacia afuera solo el LiveData
    private val _mutableVideos = MutableLiveData<List<Video>>()
    val videos : LiveData<List<Video>>
        get() = _mutableVideos

    // Now i get the videos!
    fun getVideos() {   // esto era suspend y necesita una coroutine para ser ejecutada
        val videos = repository.getVideosFromFile("videos.json")
        _mutableVideos.value = videos
    }

}