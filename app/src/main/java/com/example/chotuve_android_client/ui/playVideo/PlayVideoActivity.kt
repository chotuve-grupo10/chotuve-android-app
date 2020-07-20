package com.example.chotuve_android_client.ui.playVideo

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
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

            if(isLandScape()){
                videoView = this.findViewById(R.id.videoViewFullScreen)
                hideSystemUI()
            }

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

    private fun isLandScape(): Boolean {
        val display =
            (getSystemService(Context.WINDOW_SERVICE) as WindowManager)
                .defaultDisplay
        val rotation = display.rotation
        return (rotation == Surface.ROTATION_90
                || rotation == Surface.ROTATION_270)
    }

    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    // Shows the system bars by removing all the flags
    // except for the ones that make the content appear under the system bars.
    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }
}