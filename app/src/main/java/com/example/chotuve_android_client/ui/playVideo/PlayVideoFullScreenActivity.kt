package com.example.chotuve_android_client.ui.playVideo

import android.widget.Button
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Surface
import android.view.View
import android.view.WindowManager
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.chotuve_android_client.R
import com.example.chotuve_android_client.models.Video
import com.example.chotuve_android_client.tools.TokenHolder


class PlayVideoFullScreenActivity : AppCompatActivity() {

    private lateinit var factory : PlayVideoViewModelFactory
    private lateinit var playVideoViewModel: PlayVideoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.play_video_full_screen)
        val TAG = "PlayVideoAct"
        val video : Video? = intent.getParcelableExtra<Video>("video_to_play")
        val timeStamp : Int? = intent.getIntExtra("time", 0)
        if (video != null) {
            setPlayVideoActivityTitle(video.title.toString())

            factory = PlayVideoViewModelFactory(video)
            playVideoViewModel =
                ViewModelProviders.of(this, factory).get(PlayVideoViewModel::class.java)

            // prepare videoView
            var videoView : VideoView = this.findViewById(R.id.videoViewFullScreen)
            val mediaController = MediaController(this)
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
            supportActionBar!!.hide()

            if (!isLandScape()) {

                val intent: Intent = Intent(this, PlayVideoActivity::class.java)
                intent.putExtra("video_to_play", video)
                this.startActivity(intent)
            }

            // bind fileUrl
            Log.d(TAG, "Soy la PlayVideoActivity y estoy bindeando el filePath")
            playVideoViewModel.url.observe( this, Observer { url ->
                // Lo de abajo es la uri para un video local. Por si no funca Firebase
                // "android.resource://"+getPackageName()+"/"+R.raw.rally
                videoView.setVideoURI(Uri.parse(url))
                videoView.setMediaController(mediaController)
                videoView.requestFocus()
                if (timeStamp != null) {
                    videoView.seekTo(timeStamp)
                }
                videoView.start()
            })
        }
    }

    fun setPlayVideoActivityTitle(userName : String) {
        supportActionBar?.title = userName
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