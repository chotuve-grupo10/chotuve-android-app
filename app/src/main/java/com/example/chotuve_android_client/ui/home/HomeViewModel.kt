package com.example.chotuve_android_client.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chotuve_android_client.data.VideoRepository
import com.example.chotuve_android_client.models.VideoList
import com.example.chotuve_android_client.services.HomeListVideosService
import com.example.chotuve_android_client.services.PingService
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

    private val _textHome = MutableLiveData<String>("This is where we ping the App Server")
    val textHome : LiveData<String>
        get() = _textHome


    fun getVideosFromServer() {
        repository.getVideosForHome(_mutableVideos);
    }

    fun pingHome(myCompositeDisposable: CompositeDisposable?) {
        val pingService = PingService()
        pingService.pingServer(
            myCompositeDisposable,
            { serverStatus ->
                _textHome.value = "App Server Status:  ${serverStatus?.AppServer}\n" +
                            "Media Server Status: ${serverStatus?.MediaServer}\n" +
                            "Auth Server Status: ${serverStatus?.AuthServer}";
                Log.i("App server", "App Server Status:  ${serverStatus?.AppServer}");
                Log.i("Media Server", "Media Server Status: ${serverStatus?.MediaServer}");
                Log.i("Auth Server", "Auth Server Status: ${serverStatus?.AuthServer}");

            },
            Throwable::printStackTrace  // TODO manejar error
        )
    }


}