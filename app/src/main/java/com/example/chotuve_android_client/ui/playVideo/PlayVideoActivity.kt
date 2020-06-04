package com.example.chotuve_android_client.ui.playVideo

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.chotuve_android_client.R
import com.example.chotuve_android_client.data.model.Video


class PlayVideoActivity : AppCompatActivity() {

    private lateinit var factory : PlayVideoViewModelFactory
    private lateinit var playVideoViewModel: PlayVideoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_video)

        val video : Video? = intent.getParcelableExtra<Video>("video_to_play")
        if (video != null) {
            factory = PlayVideoViewModelFactory(video)
            playVideoViewModel =
                ViewModelProviders.of(this, factory).get(PlayVideoViewModel::class.java)

            // bind Title
            val textView: TextView = this.findViewById(R.id.playVideoActivityTitle)
            playVideoViewModel.title.observe(this,   Observer {
                textView.text = it
            })

            // prepare videoView
            val videoView : VideoView = this.findViewById(R.id.videoView)
            val mediaController = MediaController(this)

            // bind fileUrl
            Log.d("playvid", "Soy la PlayVideoActivity y estoy bindeando el filePath")
            playVideoViewModel.url.observe( this, Observer { url ->
                videoView.setVideoURI(Uri.parse(url))
                videoView.setMediaController(mediaController)
                videoView.requestFocus()
                videoView.start()
            })

        }
    }

}