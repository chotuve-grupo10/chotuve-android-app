package com.example.chotuve_android_client.ui.playVideo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import com.example.chotuve_android_client.R
import com.example.chotuve_android_client.data.model.Video
import com.example.chotuve_android_client.ui.home.HomeViewModel

class PlayVideoActivity : AppCompatActivity() {

    private lateinit var factory : PlayVideoViewModelFactory
    private lateinit var playVideoViewModel: PlayVideoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_video)


        val video : Video? = intent.getParcelableExtra<Video>("video_to_play")
        if (video != null) {
            Log.d("playvid", "Soy la PlayVideoActivity y el video es este " + video.title)
            factory = PlayVideoViewModelFactory(video)
            playVideoViewModel =
                ViewModelProviders.of(this, factory).get(PlayVideoViewModel::class.java)
        }
    }

}