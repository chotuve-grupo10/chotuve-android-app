package com.example.chotuve_android_client.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chotuve_android_client.data.VideoRepository
import com.example.chotuve_android_client.models.VideoList
import com.example.chotuve_android_client.services.HomeListVideosService
import io.reactivex.disposables.CompositeDisposable

class HomeViewModel(
    private val repository: VideoRepository
) : ViewModel() {

    companion object {
        const val TAG = "HomeViewModel"
    }
    private val homeListVideos : HomeListVideosService = HomeListVideosService()

    // Tengo mi objeto MutableLiveData pero expongo hacia afuera solo el LiveData
    private val _mutableVideos = MutableLiveData<VideoList>()
    val videos : LiveData<VideoList>
        get() = _mutableVideos

    fun getVideosFromServer() {
        repository.getVideosForHome(_mutableVideos);
    }

}