package com.example.chotuve_android_client.ui.playVideo

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Surface
import android.view.View
import android.view.WindowManager
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.chotuve_android_client.R
import com.example.chotuve_android_client.models.VideoListInner


class PlayVideoActivity : AppCompatActivity() {

    private lateinit var factory : PlayVideoViewModelFactory
    private lateinit var playVideoViewModel: PlayVideoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_video)

        val video : VideoListInner? = intent.getParcelableExtra<VideoListInner>("video_to_play")
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
            var videoView : VideoView = this.findViewById(R.id.videoView)
            val mediaController = MediaController(this)

            if (isLandScape()) {

                window.setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
                )

                videoView = this.findViewById(R.id.videoViewFullScreen)
                supportActionBar!!.hide()
            }

            // bind fileUrl
            Log.d("playvid", "Soy la PlayVideoActivity y estoy bindeando el filePath")
            playVideoViewModel.url.observe( this, Observer { url ->
                // Lo de abajo es la uri para un video local. Por si no funca Firebase
                // "android.resource://"+getPackageName()+"/"+R.raw.rally
                videoView.setVideoURI(Uri.parse(url))
                videoView.setMediaController(mediaController)
                videoView.requestFocus()
                videoView.start()
            })
        }
    }

    private fun isLandScape(): Boolean {
        val display =
            (getSystemService(Context.WINDOW_SERVICE) as WindowManager)
                .defaultDisplay
        val rotation = display.rotation
        return (rotation == Surface.ROTATION_90
                || rotation == Surface.ROTATION_270)
    }
}