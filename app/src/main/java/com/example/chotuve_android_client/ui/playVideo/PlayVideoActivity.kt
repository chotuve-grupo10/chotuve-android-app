package com.example.chotuve_android_client.ui.playVideo

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.chotuve_android_client.R
import com.example.chotuve_android_client.models.Video
import com.example.chotuve_android_client.tools.TokenHolder


class PlayVideoActivity : AppCompatActivity() {

    private lateinit var factory : PlayVideoViewModelFactory
    private lateinit var playVideoViewModel: PlayVideoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_video)

        val video : Video? = intent.getParcelableExtra<Video>("video_to_play")
        if (video != null) {
            setPlayVideoActivityTitle(video.title.toString())

            factory = PlayVideoViewModelFactory(video)
            playVideoViewModel =
                ViewModelProviders.of(this, factory).get(PlayVideoViewModel::class.java)

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

            val like_button : Button = this.findViewById<Button>(R.id.button_like)
            like_button.setBackgroundColor(ContextCompat.getColor(this, R.color.like_green))
            like_button.setOnClickListener {
                playVideoViewModel.likeVideo()
            }
//            if (video.likes!!.contains(TokenHolder.username)) {
//            }
//            else {
//                like_button.setOnClickListener {  }
//            }
            val dislike_button : Button = this.findViewById<Button>(R.id.button_dislike)
            dislike_button.setBackgroundColor(ContextCompat.getColor(this, R.color.light_red))

        }
    }

    fun setPlayVideoActivityTitle(userName : String) {
        supportActionBar?.title = userName
    }

    fun setVideoValue(video : Video) {

    }

}